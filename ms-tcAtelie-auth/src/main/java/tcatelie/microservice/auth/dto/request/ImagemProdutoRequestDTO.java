package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa uma imagem de um produto.")
public class ImagemProdutoRequestDTO {

    @Schema(description = "Identificador da imagem", example = "1")
    private Integer id;

    @Schema(description = "URL da imagem adicional do produto", example = "imagem.jpg")
    private String url;
}
