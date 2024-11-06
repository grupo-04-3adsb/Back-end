package tcatelie.microservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    private Integer id;
    private Integer quantidade;
    private Double valor;
    private Double valorTotal;
    private Double desconto;
    private Double valorDesconto;
    private Double valorFrete;
    private ProdutoResponseDTO produto;
}
