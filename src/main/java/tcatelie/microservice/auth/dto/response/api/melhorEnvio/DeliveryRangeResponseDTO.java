package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "DeliveryRangeResponseDTO", description = "Faixa de entrega")
public class DeliveryRangeResponseDTO {

    @Schema(name = "min", description = "Mínimo")
    private int min;

    @Schema(name = "max", description = "Máximo")
    private int max;
}
