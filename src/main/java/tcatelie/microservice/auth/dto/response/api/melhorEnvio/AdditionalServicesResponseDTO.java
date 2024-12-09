package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "AdditionalServicesResponseDTO", description = "Serviços adicionais")
public class AdditionalServicesResponseDTO {

    @Schema(name = "receipt", description = "Recibo")
    private boolean receipt;
    @Schema(name = "ownHand", description = "Mão própria")
    private boolean ownHand;
    @Schema(name = "collect", description = "Coleta")
    private boolean collect;
}
