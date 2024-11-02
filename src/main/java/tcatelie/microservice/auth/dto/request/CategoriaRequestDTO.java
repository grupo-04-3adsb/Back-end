package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO para a criação de uma nova categoria")
public class CategoriaRequestDTO {

    @Schema(description = "Nome da categoria", example = "Papeteria")
    private String nome;

    @Schema(description = "Código de cor da categoria", example = "#FFFFFF")
    private String codigoCor;
}
