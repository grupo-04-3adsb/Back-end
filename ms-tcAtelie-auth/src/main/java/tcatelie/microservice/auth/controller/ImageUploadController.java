package tcatelie.microservice.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tcatelie.microservice.auth.service.GoogleDriveApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@Tag(name = "Upload de Imagens", description = "Endpoints para upload, exclusão e gerenciamento de imagens no Google Drive.")
public class ImageUploadController {

    private final GoogleDriveApiService googleDriveService;

    @Operation(summary = "Upload de imagem", description = "Faz upload de uma imagem para o Google Drive e atualiza a url na imagem e retorna a URL pública da imagem.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload bem-sucedido",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Tipo de arquivo não suportado",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tipo") String tipo,
            @RequestParam("nomeProduto") String nomeProduto,
            @RequestParam("idEntidade") Integer idEntidade) throws IOException {

        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }

        String folderId = null;
        if (tipo.equals("usuario")) {
            folderId = googleDriveService.organizeUserImages();
        } else if (tipo.equals("produto") || tipo.equals("imagem-adicional")) {
            folderId = googleDriveService.organizeProductImages(nomeProduto);
        } else if (tipo.equals("opcaoPersonalizacao")) {
            folderId = googleDriveService.organizeOptionImages(nomeProduto);
        }

        if (folderId != null) {
            String fileId = googleDriveService.uploadFileToFolder(convFile, file.getOriginalFilename(), folderId);
            convFile.delete();

            String publicUrl = googleDriveService.getPublicUrl(fileId);

            googleDriveService.salvarUrlEntidade(tipo, idEntidade, publicUrl);

            return ResponseEntity.ok(publicUrl);
        }

        return ResponseEntity.badRequest().body("Tipo de arquivo não suportado");
    }

    @Operation(summary = "Deletar arquivo ou pasta", description = "Remove um arquivo ou pasta do Google Drive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo/pasta removido com sucesso",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao remover arquivo/pasta",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/delete")
    public ResponseEntity<String> deleteFileOrFolder(@RequestParam("fileId") String fileId) {
        try {
            googleDriveService.deleteFileOrFolder(fileId);
            return ResponseEntity.ok("Arquivo/pasta removido com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao remover o arquivo/pasta: " + e.getMessage());
        }
    }

    @Operation(summary = "Deletar pasta de produto", description = "Remove a pasta de um produto específico e todo o seu conteúdo do Google Drive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pasta do produto removida com sucesso",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Pasta de produtos ou do produto não encontrada",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao remover a pasta do produto",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/delete-product-folder")
    public ResponseEntity<String> deleteProductFolder(@RequestParam("nomeProduto") String nomeProduto) {
        try {
            String productsFolderId = googleDriveService.findFolderByName("produtos", googleDriveService.getRootFolderId());
            if (productsFolderId == null) {
                return ResponseEntity.badRequest().body("Pasta de produtos não encontrada.");
            }

            String productFolderId = googleDriveService.findFolderByName(nomeProduto, productsFolderId);
            if (productFolderId == null) {
                return ResponseEntity.badRequest().body("Pasta do produto '" + nomeProduto + "' não encontrada.");
            }

            googleDriveService.deleteFolderAndContents(productFolderId);
            return ResponseEntity.ok("Pasta do produto e seus arquivos foram removidos com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao remover a pasta do produto: " + e.getMessage());
        }
    }

}
