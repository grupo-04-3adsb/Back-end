package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "DimensionsResponseDTO", description = "Dimensões")
public class DimensionsResponseDTO {

    @Schema(name = "height", description = "Altura")
    private int height;
    @Schema(name = "width", description = "Largura")
    private int width;
    @Schema(name = "length", description = "Comprimento")
    private int length;
}
