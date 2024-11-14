package tcatelie.microservice.auth.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoriaFiltroDTO {
    @Schema(description = "Nome da subcategoria buscada", example = "Cadernos")
    private String nomeSubcategoria;
    @Schema(description = "Descrição da subcategoria buscada", example = "Cadernos de capa dura")
    private String descricaoSubcategoria;
    @Schema(description = "Quantidade mínima de produtos", example = "10")
    private Integer quantidadeMinimaProdutos;
    @Schema(description = "Quantidade máxima de produtos", example = "100")
    private Integer quantidadeMaximaProdutos;
    @Schema(description = "Nome da categoria buscada", example = "Cadernos")
    private String nomeCategoria;
    @Schema(description = "Data de cadastro início", example = "2021-01-01T00:00:00")
    private LocalDateTime dataCadastroInicio;
    @Schema(description = "Data de cadastro fim", example = "2021-01-01T00:00:00")
    private LocalDateTime dataCadastroFim;
    @Schema(description = "Data de atualização inicial", example = "2021-01-01T00:00:00")
    private LocalDateTime dataAtualizacaoInicio;
    @Schema(description = "Data de atualização final", example = "2021-01-01T00:00:00")
    private LocalDateTime dataAtualizacaoFim;
}
