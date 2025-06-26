package tcatelie.microservice.auth.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobDownloadContentResponse;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.*;
import tcatelie.microservice.auth.util.StringUtilsHelp;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService {

  @Value("${azure.blob.storage.url}")
  private String BLOB_STORAGE_URL;

  @Value("${azure.blob.storage.token.sas}")
  private String BLOB_STORAGE_TOKEN_SAS;

  private static final Logger LOGGER = LoggerFactory.getLogger(AzureBlobStorageService.class);

  private final ProdutoRepository produtoRepository;
  private final UserRepository userRepository;
  private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;
  private final ImagensProdutoRepository imagensProdutoRepository;
  private final PersonalizacaoItemPedidoRepository personalizacaoItemPedidoRepository;

  public String uploadFile(MultipartFile file, String virtualPath) throws IOException {
    BlobContainerClient containerClient = new BlobContainerClientBuilder()
            .endpoint(BLOB_STORAGE_URL + "/images?" + BLOB_STORAGE_TOKEN_SAS)
            .buildClient();

    BlobHttpHeaders headers = new BlobHttpHeaders()
            .setContentType(file.getContentType());

    BlobClient blobClient = containerClient.getBlobClient(virtualPath);

    blobClient.upload(file.getInputStream(), file.getSize(), true);
    blobClient.setHttpHeaders(headers);

    return blobClient.getBlobUrl();
  }

  public String resolveVirtualPath(String tipo, String nomeProduto, Integer idEntidade, MultipartFile file) {
    String extension = getFileExtension(file.getOriginalFilename());

    if (nomeProduto == null || nomeProduto.isEmpty() || nomeProduto.equalsIgnoreCase("undefined")) {
      Produto produto = produtoRepository.findById(idEntidade).orElse(null);
      if (produto != null) {
        nomeProduto = produto.getNome();
      } else {
        nomeProduto = "default_%s_%d".formatted(tipo, idEntidade);
      }
    }

    String folder = switch (tipo) {
      case "usuario" -> "usuarios";
      case "produto" -> "produtos/" + nomeProduto;
      case "imagem-adicional" -> "produtos/" + nomeProduto + "/imagens-adicionais";
      case "opcaoPersonalizacao" -> "produtos/" + nomeProduto + "/opcoes";
      case "personalizacaoItem" -> "itens_pedido/" + idEntidade;
      default -> throw new IllegalArgumentException("Tipo inválido");
    };

    return folder + "/" + UUID.randomUUID() + extension;
  }

  private String getFileExtension(String filename) {
    if (filename == null || !filename.contains(".")) return ".jpg";
    return filename.substring(filename.lastIndexOf(".")).toLowerCase();
  }

  public void removeFile(String fileUrl) {
    try {
      if (StringUtils.isEmpty(fileUrl)) {
        LOGGER.warn("A URL do arquivo está vazia ou nula. Não é possível remover o arquivo.");
        return;
      }

      LOGGER.info("Removendo arquivo: {}", fileUrl);

      String containerName;
      String blobPath;

      if (fileUrl.contains("/documents/")) {
        containerName = "documents";
        blobPath = extractBlobPath(fileUrl, "/documents/");
      } else if (fileUrl.contains("/images/")) {
        containerName = "images";
        blobPath = extractBlobPath(fileUrl, "/images/");
      } else {
        LOGGER.error("URL não contém um container válido (/documents/ ou /images/): {}", fileUrl);
        return;
      }

      if (StringUtils.isEmpty(blobPath)) {
        LOGGER.error("Não foi possível extrair o blobPath da URL: {}", fileUrl);
        return;
      }

      BlobContainerClient containerClient = new BlobContainerClientBuilder()
              .endpoint(BLOB_STORAGE_URL)
              .sasToken(BLOB_STORAGE_TOKEN_SAS)
              .containerName(containerName)
              .buildClient();

      BlobClient blobClient = containerClient.getBlobClient(blobPath);
      blobClient.delete();

      LOGGER.info("Arquivo removido com sucesso: container={}, path={}", containerName, blobPath);

    } catch (Exception e) {
      LOGGER.error("Erro ao remover o arquivo: {}", e.getMessage(), e);
    }
  }

  public void salvarUrlEntidade(String tipo, Integer idEntidade, String urlAcesso, String blobPath) {
    if (tipo.equals("usuario")) {
      Usuario usuario = userRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

      removeFile(usuario.getUrlImgUsuario());

      usuario.setUrlImgUsuario(urlAcesso);
      usuario.setIdImgDrive(blobPath);
      userRepository.save(usuario);
    } else if (tipo.equals("produto")) {
      Produto produto = produtoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

      removeFile(produto.getUrlImagemPrincipal());

      produto.setUrlImagemPrincipal(urlAcesso);
      produto.setIdImgDrive(blobPath);
      produtoRepository.save(produto);
    } else if (tipo.equals("opcaoPersonalizacao")) {
      OpcaoPersonalizacao opcao = opcaoPersonalizacaoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção não encontrada"));

      removeFile(opcao.getUrlImagemOpcao());

      opcao.setUrlImagemOpcao(urlAcesso);
      opcao.setIdImgDrive(blobPath);
      opcaoPersonalizacaoRepository.save(opcao);
    } else if (tipo.equals("imagem-adicional")) {
      ImagensProduto imgProduto = imagensProdutoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagem adicional não encontrada"));

      removeFile(imgProduto.getUrlImgAdicional());

      imgProduto.setUrlImgAdicional(urlAcesso);
      imgProduto.setIdImgDrive(blobPath);
      imagensProdutoRepository.save(imgProduto);
    } else if (tipo.equals("personalizacaoItem")) {
      PersonalizacaoItemPedido personalizacaoItem = personalizacaoItemPedidoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização do item não encontrada"));

      removeFile(personalizacaoItem.getIdImgDrive());

      personalizacaoItem.setDescricaoPersonalizacao(urlAcesso);
      personalizacaoItem.setIdImgDrive(blobPath);
      personalizacaoItemPedidoRepository.save(personalizacaoItem);
    }
  }

  public String uploadPdf(MultipartFile file, String nomeArquivoDestino) {
    if (file == null || file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo inválido");
    }

    try {
      BlobContainerClient containerClient = new BlobContainerClientBuilder()
              .endpoint(BLOB_STORAGE_URL + "/documents?" + BLOB_STORAGE_TOKEN_SAS)
              .buildClient();

      String extension = getFileExtension(file.getOriginalFilename());
      if (!extension.equals(".pdf")) {
        throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Apenas arquivos PDF são permitidos");
      }

      String nomeFormatado = StringUtils.isEmpty(nomeArquivoDestino) ? StringUtilsHelp.formatarNomeArquivo(nomeArquivoDestino) : UUID.randomUUID().toString();

      String virtualPath = "pdfs/" + nomeFormatado + extension;
      BlobClient blobClient = containerClient.getBlobClient(virtualPath);

      BlobHttpHeaders headers = new BlobHttpHeaders()
              .setContentType("application/pdf")
              .setContentDisposition("attachment; filename=\"" + nomeFormatado + "\"");

      blobClient.upload(file.getInputStream(), file.getSize(), true);
      blobClient.setHttpHeaders(headers);

      return blobClient.getBlobUrl();
    } catch (IOException e) {
      LOGGER.error("Erro ao fazer upload do PDF: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar o upload");
    }
  }

  private String extractBlobPath(String fileUrl, String containerPrefix) {
    try {
      URI uri = new URI(fileUrl);
      String fullPath = uri.getPath();
      return fullPath.substring(fullPath.indexOf(containerPrefix) + containerPrefix.length());
    } catch (Exception e) {
      LOGGER.error("Erro ao extrair o blobPath da URL: {}", e.getMessage(), e);
      return null;
    }
  }

  public ResponseEntity<ByteArrayResource> downloadPdf(String url) {
    try {
      BlobContainerClient containerClient = new BlobContainerClientBuilder()
              .endpoint(BLOB_STORAGE_URL)
              .sasToken(BLOB_STORAGE_TOKEN_SAS)
              .containerName("documents")
              .buildClient();

      String blobPath = extractBlobPath(url, "/documents/");

      BlobClient blobClient = containerClient.getBlobClient(blobPath);

      if (!blobClient.exists()) {
        LOGGER.warn("PDF não encontrado no blob path: {}", blobPath);
        return ResponseEntity.notFound().build();
      }

      BinaryData response = blobClient.downloadContent();
      byte[] pdfBytes = response.toBytes();

      ByteArrayResource resource = new ByteArrayResource(pdfBytes);

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + blobClient.getBlobName())
              .contentType(MediaType.APPLICATION_PDF)
              .contentLength(pdfBytes.length)
              .body(resource);

    } catch (Exception e) {
      LOGGER.error("Erro ao baixar o PDF do Azure Blob Storage: {}", e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }
}