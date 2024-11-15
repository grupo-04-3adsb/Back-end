package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para atualização da categoria e subcategoria de produtos")
public class ProdutosUpdateRequestDTO {

    @NotEmpty(message = "A lista de nomes de produtos não pode ser vazia")
    @Schema(description = "Lista de nomes de produtos", example = "[\"Topo de Bolo Personalizado\"]")
    private List<String> nomesProdutos;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Schema(description = "Nome da categoria", example = "Topo de Bolo")
    private String nomeCategoria;

    @NotBlank(message = "O nome da subcategoria é obrigatório")
    @Schema(description = "Nome da subcategoria", example = "Personalizado")
    private String nomeSubcategoria;

    @NotBlank(message = "A categoria antiga é obrigatória")
    @Schema(description = "Categoria antiga", example = "Topo de Bolo")
    private String categoriaAntiga;
}
