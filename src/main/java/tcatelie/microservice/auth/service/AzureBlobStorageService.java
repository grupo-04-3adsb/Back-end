package tcatelie.microservice.auth.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.*;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AzureBlobStorageService {

  private static final String BLOB_CONTAINER_SAS_URL = "https://tcateliestorage.blob.core.windows.net/images?sv=2024-11-04&ss=bf&srt=sco&sp=rwdlaciytfx&se=2026-01-02T00:35:09Z&st=2025-04-06T16:35:09Z&spr=https&sig=GkEUWUV%2FoA7aWKyfV1jOicMdGqhafBIJVo%2B9Y23Wop4%3D";

  private final ProdutoRepository produtoRepository;
  private final UserRepository userRepository;
  private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;
  private final ImagensProdutoRepository imagensProdutoRepository;
  private final PersonalizacaoItemPedidoRepository personalizacaoItemPedidoRepository;

  public String uploadFile(MultipartFile file, String virtualPath) throws IOException {
    BlobContainerClient containerClient = new BlobContainerClientBuilder()
            .endpoint(BLOB_CONTAINER_SAS_URL)
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

  public void salvarUrlEntidade(String tipo, Integer idEntidade, String urlAcesso, String blobPath) {
    if (tipo.equals("usuario")) {
      Usuario usuario = userRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
      usuario.setUrlImgUsuario(urlAcesso);
      usuario.setIdImgDrive(blobPath);
      userRepository.save(usuario);
    } else if (tipo.equals("produto")) {
      Produto produto = produtoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
      produto.setUrlImagemPrincipal(urlAcesso);
      produto.setIdImgDrive(blobPath);
      produtoRepository.save(produto);
    } else if (tipo.equals("opcaoPersonalizacao")) {
      OpcaoPersonalizacao opcao = opcaoPersonalizacaoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção não encontrada"));
      opcao.setUrlImagemOpcao(urlAcesso);
      opcao.setIdImgDrive(blobPath);
      opcaoPersonalizacaoRepository.save(opcao);
    } else if (tipo.equals("imagem-adicional")) {
      ImagensProduto imgProduto = imagensProdutoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagem adicional não encontrada"));
      imgProduto.setUrlImgAdicional(urlAcesso);
      imgProduto.setIdImgDrive(blobPath);
      imagensProdutoRepository.save(imgProduto);
    } else if (tipo.equals("personalizacaoItem")) {
      PersonalizacaoItemPedido personalizacaoItem = personalizacaoItemPedidoRepository.findById(idEntidade).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização do item não encontrada"));
      personalizacaoItem.setDescricaoPersonalizacao(urlAcesso);
      personalizacaoItem.setIdImgDrive(blobPath);
      personalizacaoItemPedidoRepository.save(personalizacaoItem);
    }
  }

}
