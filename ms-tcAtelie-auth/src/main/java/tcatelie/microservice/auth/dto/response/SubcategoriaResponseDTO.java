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

    @Schema(description = "Descrição da subcategoria", example = "Cadernos para organizar suas ideias mais inovadoras.")
    private String descricaoSubcategoria;
}
