package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para a criação de uma nova subcategoria")
public class SubcategoriaRequestDTO {

    @Schema(description = "Nome da subcategoria", example = "Cadernos")
    private String nomeSubcategoria;

    @Schema(description = "Descrição da subcategoria", example = "Cadernos personalizados para todas as idades")
    private String descricaoSubcategoria;
}
