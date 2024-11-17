package tcatelie.microservice.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.dto.PersonalizacaoItemPedidoResponseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    private Integer id;
    @Schema(description = "Quantidade de produtos", example = "10")
    private Integer quantidade;
    @Schema(description = "Valor unitário do produto", example = "10.00")
    private Double valor;
    @Schema(description = "Valor pago por este item com frete, desconto, valor unitário * qauntidade + custo da personalizacao", example = "100.00")
    private Double valorTotal;
    @Schema(description = "Desconto aplicado no item %", example = "10.00")
    private Double desconto;
    @Schema(description = "Valor do desconto aplicado no item R$", example = "10.00")
    private Double valorDesconto;
    @Schema(description = "Valor do frete aplicado no item", example = "10.00")
    private Double valorFrete;
    @Schema(description = "Custo de produção do item ao concluir confecção", example = "10.00")
    private Double custoProducao;
    @Schema(description = "Responsável confeccionou o produto", example = "true")
    private Boolean feito;
    private ProdutoResponseDTO produto;
    private List<PersonalizacaoItemPedidoResponseDTO> personalizacoes;
}
