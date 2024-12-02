package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "AdditionalResponseDTO", description = "Serviços adicionais")
public class AdditionalResponseDTO {

    @Schema(name = "unidade", description = "Unidade")
    private UnitResponseDTO unit;

}
