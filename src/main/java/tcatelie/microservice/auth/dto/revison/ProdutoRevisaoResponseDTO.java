package tcatelie.microservice.auth.dto.revison;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoRevisaoResponseDTO {

    @Schema(description = "Identificador do produto.")
    private Integer idProduto;

    @Schema(description = "Nome do produto.")
    private String nomeProduto;

    @Schema(description = "Margem de lucro antiga do produto.")
    private Double margemDeLucroAntiga;

    @Schema(description = "Nova margem de lucro do produto.")
    private Double margemDeLucroNova;

}
