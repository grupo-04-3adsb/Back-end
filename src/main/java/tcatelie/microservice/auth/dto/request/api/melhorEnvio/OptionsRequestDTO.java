package tcatelie.microservice.auth.dto.request.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OptionsRequestDTO", description = "Opções")
public class OptionsRequestDTO {

    @Schema(name = "receipt", description = "Recibo")
    private boolean receipt;

    @Schema(name = "own_hand", description = "Mão própria")
    private boolean own_hand;
}