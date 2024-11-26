package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ShippingResponseDTO", description = "Resposta de frete")
public class ShippingResponseDTO {

    @Schema(name = "options", description = "Opções de frete")
    private ShippingOptionResponseDTO[] options;
}
