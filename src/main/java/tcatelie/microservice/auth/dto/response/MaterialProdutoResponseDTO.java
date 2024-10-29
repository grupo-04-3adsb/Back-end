package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa um material de um produto")
public class MaterialProdutoResponseDTO {

    @Schema(description = "Quantidade necessária para produção", example = "2")
    private Integer qtdParaProducao;

    @Schema(description = "Custo total de produção da material", example = "30.00")
    private Double custoProducao;

    @Schema(description = "ID do material", example = "1")
    private Integer idMaterial;

    @Schema(description = "Nome do material", example = "Madeira")
    private String nomeMaterial;

    @Schema(description = "Estoque disponível do material", example = "100")
    private Integer estoque;

    @Schema(description = "Preço unitário do material", example = "15.00")
    private Double precoUnitario;
}
