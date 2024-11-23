package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "UnitResponseDTO", description = "Unidade")
public class UnitResponseDTO {

    @Schema(name = "preco", description = "Preço")
    private BigDecimal price;

    @Schema(name = "delivery", description = "Entrega")
    private BigDecimal delivery;
}
