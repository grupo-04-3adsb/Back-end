package tcatelie.microservice.auth.dto.revison;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRevisaoResponseDTO {

    @Schema(description = "Identificador do material.")
    private Integer idMaterial;

    @Schema(description = "Preço unitário antigo do material.")
    private Double precoUnitarioAntigo;

    @Schema(description = "Preço unitário novo do material.")
    private Double precoUnitarioNovo;

}
