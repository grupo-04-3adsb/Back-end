package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa uma imagem de um produto.")
public class ImagemProdutoResponseDTO {

    @Schema(description = "ID da imagem adicional", example = "1")
    private Integer idImagemAdicional;

    @Schema(description = "ID da imagem no Google Drive", example = "1")
    private String idImgDrive;

    @Schema(description = "URL da imagem", example = "https://drive.google.com/...")
    private String url;
}
