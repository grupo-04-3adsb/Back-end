package tcatelie.microservice.auth.dto.response.api.melhorEnvio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema
public class PackageResponseDTO {

    @Schema(name = "preco", description = "Preço")
    private BigDecimal price;

    @Schema(name = "desconto", description = "Desconto")
    private BigDecimal discount;

    @Schema(name = "formato", description = "Formato")
    private String format;

    @Schema(name = "peso", description = "Peso")
    private BigDecimal weight;

    @Schema(name = "valor_segurado", description = "Valor segurado")
    private BigDecimal insuranceValue;

    @Schema(name = "produtos", description = "Produtos")
    private ProductResponseDTO[] products;

    @Schema(name = "dimensoes", description = "Dimensões")
    private DimensionsResponseDTO dimensions;

}
