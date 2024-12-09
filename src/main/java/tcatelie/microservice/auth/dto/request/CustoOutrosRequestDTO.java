package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CustoOutrosRequestDTO", description = "DTO para requisição de custos de outros")
public class CustoOutrosRequestDTO {

    @Schema(description = "ID do custo de outros", example = "1")
    private Integer id;

    @Schema(description = "Nome do custo de outros", example = "Custo de outros 1")
    private String descricao;

    @Schema(description = "Valor do custo de outros", example = "100.00")
    private Double valor;

    @Schema(description = "Data e hora da atualização do custo de outros", example = "2021-08-01T00:00:00")
    private String dataHoraAtualizacao;

    @Schema(description = "Data e hora da criação do custo de outros", example = "2021-08-01T00:00:00")
    private String dataHoraCriacao;
}
