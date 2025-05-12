package tcatelie.microservice.auth.dto.response.item_pedido;

import lombok.Builder;
import tcatelie.microservice.auth.dto.response.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.produto.ProdutoResumidoResponseDTO;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

import java.util.List;

@Builder
public record ItemPedidoResumidoResponseDTO(
        Integer id,
        Integer quantidade,
        Double valor,
        Double valorTotal,
        Double frete,
        List<PersonalizacaoItemPedidoResponseDTO> personalizacoes,
        ProdutoResumidoResponseDTO produto
) {
}
