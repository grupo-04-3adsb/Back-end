package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Item de pedido")
public class ItemPedidoRequestDTO {

    @Schema(description = "Identificador do item do pedido")
    private Integer id;

    @Schema(description = "Quantidade do item")
    @Min(value = 1, message = "A quantidade deve ser maior que 0")
    private Integer quantidade;

    @Schema(description = "Valor unitário do item")
    private Double valor;

    @Schema(description = "Valor total do item")
    private Double valorTotal;

    @Schema(description = "Desconto aplicado ao item")
    private Double desconto;

    @Schema(description = "Valor do desconto aplicado ao item")
    private Double valorDesconto;

    @Schema(description = "Valor do frete do item")
    private Double valorFrete;

    @Schema(description = "Custo de produção do item")
    private Double custoProducao;

    @Schema(description = "Indica se o produto foi feito")
    private Boolean produtoFeito;

    @Schema(description = "Lista de personalizações do item")
    private List<PersonalizacaoItemPedidoRequestDTO> personalizacoes;

    @Schema(description = "Identificador do produto")
    @NotNull(message = "O produto é obrigatório")
    private Integer fkProduto;

    @Schema(description = "Identificador do pedido")
    private Integer fkPedido;
}