package tcatelie.microservice.auth.dto.request.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Product", description = "Produto")
public class ProductRequestDTO {

    @Schema(name = "id", description = "ID")
    private String id;

    @Schema(name = "width", description = "Largura")
    private int width;

    @Schema(name = "height", description = "Altura")
    private int height;

    @Schema(name = "length", description = "Comprimento")
    private int length;

    @Schema(name = "weight", description = "Peso")
    private double weight;

    @Schema(name = "insurance_value", description = "Valor do seguro")
    private double insurance_value;

    @Schema(name = "quantity", description = "Quantidade")
    private int quantity;

}
