package tcatelie.microservice.auth.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "CustoOutrosFiltroDTO", description = "DTO para filtro de custos de outros")
public class CustoOutrosFiltroDTO {

    @Schema(description = "Nome do custo de outros", example = "Custo de outros 1")
    private String descricao;

    @Schema(description = "Valor mínimo do custo de outros", example = "100.00")
    private Double valorMin;

    @Schema(description = "Valor máximo do custo de outros", example = "200.00")
    private Double valorMax;

    @Schema(description = "Data e hora da atualização do custo de outros", example = "2021-08-01T00:00:00")
    private String dataHoraAtualizacao;

    @Schema(description = "Data e hora da criação do custo de outros", example = "2021-08-01T00:00:00")
    private String dataHoraCriacao;
}
