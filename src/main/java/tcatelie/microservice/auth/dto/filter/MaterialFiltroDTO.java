package tcatelie.microservice.auth.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialFiltroDTO {

    @Schema(description = "Nome do material.", example = "Algodão")
    private String nomeMaterial;

    @Schema(description = "Preço unitário mínimo.", example = "10.0")
    private Double precoUnitarioMinimo;

    @Schema(description = "Preço unitário máximo.", example = "20.0")
    private Double precoUnitarioMaximo;

    @Schema(description = "Data e hora de cadastro inicial.", example = "2021-01-01T00:00:00")
    private LocalDateTime dataHoraCadastroInicio;

    @Schema(description = "Data e hora de cadastro final.", example = "2021-12-31T23:59:59")
    private LocalDateTime dataHoraCadastroFim;

    @Schema(description = "Data e hora de atualização inicial.", example = "2021-01-01T00:00:00")
    private LocalDateTime dataHoraAtualizacaoInicio;

    @Schema(description = "Data e hora de atualização final.", example = "2021-12-31T23:59:59")
    private LocalDateTime dataHoraAtualizacaoFim;

}
