package tcatelie.microservice.auth.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Filtro de busca para produtos")
public class ProdutoFiltroDTO {

    @Schema(description = "Nome do produto para busca parcial", example = "Topo de Bolo Personalizado")
    private String nome;

    @Schema(description = "SKU do produto para busca exata", example = "SKU123456")
    private String sku;

    @Schema(description = "Margem de lucro mínima do produto", example = "5.00")
    private Double margemLucroMinima;

    @Schema(description = "Margem de lucro máxima do produto", example = "20.00")
    private Double margemLucroMaxima;

    @Schema(description = "Preço de venda mínimo do produto", example = "50.00")
    private Double precoMinimo;

    @Schema(description = "Preço de venda máximo do produto", example = "100.00")
    private Double precoMaximo;

    @Schema(description = "ID da categoria para filtragem", example = "1")
    private Integer idCategoria;

    @Schema(description = "ID da subcategoria para filtragem", example = "2")
    private Integer idSubcategoria;

    @Schema(description = "Indica se o produto é personalizável", example = "true")
    private Boolean isPersonalizavel;

    @Schema(description = "Indica se a personalização é obrigatória para o produto", example = "true")
    private Boolean IsPersonalizacaoObrigatoria;
}
