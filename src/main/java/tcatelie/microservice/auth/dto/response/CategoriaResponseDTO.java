package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa uma categoria.")
public class CategoriaResponseDTO {

    @Schema(description = "ID da categoria", example = "1")
    private Integer idCategoria;

    @Schema(description = "Nome da categoria", example = "Cadernos")
    private String nomeCategoria;

    @Schema(description = "Lista de subcategorias pertencentes à categoria")
    private List<SubcategoriaResponseDTO> subcategorias;

    @Schema(description = "Data e hora de cadastro da categoria", example = "12:00 | 01/10/2024")
    private String dthrCadastro;

    @Schema(description = "Data e hora de atualização da categoria", example = "12:00 | 01/10/2024")
    private String dthrAtualizacao;

    @Schema(description = "Quantidade de produtos pertencentes à categoria", example = "10")
    private Integer qtdProdutosCategoria;

    @Schema(description = "Quantidade de subcategorias pertencentes à categoria", example = "5")
    private Integer qtdSubcategorias;

    @Schema(description = "Código de cor da categoria", example = "#FFFFFF")
    private String codigoCor;
}
