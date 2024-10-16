package tcatelie.microservice.auth.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.model.ImagensProduto;
import tcatelie.microservice.auth.model.OpcaoPersonalizacao;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.ImagensProdutoRepository;
import tcatelie.microservice.auth.repository.OpcaoPersonalizacaoRepository;
import tcatelie.microservice.auth.repository.ProdutoRepository;
import tcatelie.microservice.auth.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleDriveApiService {

    private final Drive driveService;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;
    private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;
    private final ImagensProdutoRepository imagensProdutoRepository;

    @Getter
    private final String rootFolderId = "16yN_yD1JbDVQUssFowEu1DuUVfrgwJkq";


    public String findFolderByName(String folderName, String parentFolderId) throws IOException {
        String query = String.format("mimeType='application/vnd.google-apps.folder' and name='%s' and '%s' in parents", folderName, parentFolderId);

        FileList result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id, name)")
                .execute();

        if (result.getFiles().isEmpty()) {
            return null;
        }

        return result.getFiles().get(0).getId();
    }

    public String createFolder(String folderName, String parentFolderId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        File folder = driveService.files().create(fileMetadata)
                .setFields("id")
                .execute();

        return folder.getId();
    }

    public String getPublicUrl(String fileId) {
        return "https://drive.google.com/thumbnail?id=" + fileId + "&sz=w1000";
    }

    public void makeFilePublic(String fileId) throws IOException {
        Permission publicPermission = new Permission()
                .setType("anyone")
                .setRole("reader");

        driveService.permissions().create(fileId, publicPermission).execute();
    }

    public String uploadFileToFolder(java.io.File file, String fileName, String parentFolderId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(parentFolderId));

        FileContent mediaContent = new FileContent("image/jpeg", file);

        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        makeFilePublic(uploadedFile.getId());

        return uploadedFile.getId();
    }


    public void deleteFileOrFolder(String fileId) throws IOException {
        driveService.files().delete(fileId).execute();
    }

    public void deleteFolderAndContents(String folderId) throws IOException {
        FileList fileList = driveService.files().list()
                .setQ("'" + folderId + "' in parents")
                .setFields("files(id)")
                .execute();

        for (File file : fileList.getFiles()) {
            deleteFileOrFolder(file.getId());
        }

        deleteFileOrFolder(folderId);
    }

    public void removerImagemPorUrl(String urlImagem) throws IOException {
        String fileId = extrairIdDaUrl(urlImagem);

        deleteFileOrFolder(fileId);
    }

    private String extrairIdDaUrl(String url) {
        String[] parts = url.split("id=");
        if (parts.length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL da imagem inválida.");
        }
        return parts[1];
    }

    public String organizeUserImages() throws IOException {
        String rootFolderId = getRootFolderId();
        String usersFolderId = findFolderByName("usuarios", rootFolderId);

        if (usersFolderId == null) {
            usersFolderId = createFolder("usuarios", rootFolderId);
        }

        return usersFolderId;
    }

    public String organizeProductImages(String nomeProduto) throws IOException {
        String rootFolderId = getRootFolderId();
        String productsFolderId = findFolderByName("produtos", rootFolderId);

        if (productsFolderId == null) {
            productsFolderId = createFolder("produtos", rootFolderId);
        }

        String productFolderId = findFolderByName(nomeProduto, productsFolderId);

        if (productFolderId == null) {
            productFolderId = createFolder(nomeProduto, productsFolderId);
        }

        return productFolderId;
    }

    public String organizeOptionImages(String nomeProduto) throws IOException {
        String productsFolderId = organizeProductImages(nomeProduto);

        String optionsFolderId = findFolderByName("opcoes", productsFolderId);

        if (optionsFolderId == null) {
            optionsFolderId = createFolder("opcoes", productsFolderId);
        }

        return optionsFolderId;
    }

    public void salvarUrlEntidade(String tipo, Integer idEntidade, String urlAcesso, String idImagemDrive) {
        if (tipo.equals("usuario")) {
            Usuario usuario = userRepository.findById(idEntidade).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com id %d".formatted(idEntidade))
            );
            usuario.setUrlImgUsuario(urlAcesso);
            usuario.setIdImgDrive(idImagemDrive);
            userRepository.save(usuario);
        } else if (tipo.equals("produto")) {
            Produto produto = produtoRepository.findById(idEntidade).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com id %d".formatted(idEntidade))
            );
            produto.setUrlImagemPrincipal(urlAcesso);
            produto.setIdImgDrive(idImagemDrive);
            produtoRepository.save(produto);
        } else if (tipo.equals("opcaoPersonalizacao")) {
            OpcaoPersonalizacao opcao = opcaoPersonalizacaoRepository.findById(idEntidade).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção não encontrada com id %d".formatted(idEntidade))
            );
            opcao.setUrlImagemOpcao(urlAcesso);
            opcao.setIdImgDrive(idImagemDrive);
            opcaoPersonalizacaoRepository.save(opcao);
        } else if (tipo.equals("imagem-adicional")) {
            ImagensProduto imgProduto = imagensProdutoRepository.findById(idEntidade).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagem adicional não encontrada com id %d".formatted(idEntidade))
            );
            imgProduto.setUrlImgAdicional(urlAcesso);
            imgProduto.setIdImgDrive(idImagemDrive);
            imagensProdutoRepository.save(imgProduto);
        }
    }

}
