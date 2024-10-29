package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO que representa um produto do Mercado Livre.")
public class MercadoLivreProdutoResponseDTO {

    @Schema(description = "ID do produto.", example = "MLB123456789")
    private String id;

    @Schema(description = "Título do produto.", example = "Camiseta")
    private String title;

    @Schema(description = "ID da categoria do produto.", example = "MLB123456")
    private String categoryId;

    @Schema(description = "Condição do produto.", example = "new")
    private String condition;

    @Schema(description = "Preço do produto.", example = "100.00")
    private BigDecimal price;

    @Schema(description = "ID da moeda do produto.", example = "BRL")
    private String currencyId;

    @Schema(description = "URL da imagem do produto.", example = "https://www.mercadolivre.com.br/produto.jpg")
    private String thumbnail;

    @Schema(description = "URL do produto.", example = "https://www.mercadolivre.com")
    private String permalink;

    @Schema(description = "Quantidade disponível do produto.", example = "10")
    private int availableQuantity;

    @Schema(description = "Quantidade vendida do produto.", example = "5")
    private int soldQuantity;
}
