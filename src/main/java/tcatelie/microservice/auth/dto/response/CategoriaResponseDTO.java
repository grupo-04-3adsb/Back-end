package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponseDTO {

    @Schema(description = "ID da categoria", example = "1")
    private Integer idCategoria;

    @Schema(description = "Nome da categoria", example = "Cadernos")
    private String nomeCategoria;

    @Schema(description = "Lista de subcategorias pertencentes à categoria")
    private List<SubcategoriaResponseDTO> subcategorias;
}
