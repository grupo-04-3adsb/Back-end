package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "ProductResponseDTO", description = "Produto")
public class ProductResponseDTO {

    @Schema(name = "id", description = "Identificador do produto")
    private String id;
    @Schema(name = "name", description = "Nome do produto")
    private int quantity;

}
