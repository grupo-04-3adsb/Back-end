package tcatelie.microservice.auth.dto.request.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "From", description = "Endereço de origem")
public class FromRequestDTO {
    @Schema(name = "postal_code", description = "CEP")
    private String postal_code;
}