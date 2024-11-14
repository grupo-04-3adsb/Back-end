package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa uma subcategoria.")
public class SubcategoriaResponseDTO {

    @Schema(description = "ID da subcategoria", example = "1")
    private Integer idSubcategoria;

    @Schema(description = "Nome da subcategoria", example = "Caderno inteligente")
    private String nomeSubcategoria;

    @Schema(description = "Descrição da subcategoria", example = "Caderno inteligente com folhas pautadas")
    private String descricaoSubcategoria;

    @Schema(description = "Data e hora de cadastro da subcategoria", example = "12:00 | 01/10/2024")
    private String dthrCadastro;

    @Schema(description = "Data e hora de atualização da subcategoria", example = "12:00 | 01/10/2024")
    private String dthrAtualizacao;

    @Schema(description = "Quantidade de produtos pertencentes à subcategoria", example = "10")
    private Integer qtdProdutosSubcategoria;

    @Schema(description = "Nome da categoria à qual a subcategoria pertence", example = "Cadernos")
    private String nomeCategoria;

    @Schema(description = "Código de cor da subcategoria", example = "#FFFFFF")
    private String codigoCor;
}
