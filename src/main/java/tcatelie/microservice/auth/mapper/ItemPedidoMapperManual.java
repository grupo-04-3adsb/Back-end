package tcatelie.microservice.auth.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.response.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.item_pedido.ItemPedidoResumidoResponseDTO;
import tcatelie.microservice.auth.model.ItemPedido;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemPedidoMapperManual {

  private final PersonalizacaoItemPedidoMapper personalizacaoItemPedidoMapper;

  public static ItemPedidoResumidoResponseDTO toItemPedidoResumidoResponseDTO(ItemPedido entity) {

    return ItemPedidoResumidoResponseDTO.builder()
            .id(entity.getId())
            .quantidade(entity.getQuantidade())
            .valor(entity.getValor())
            .valorTotal(entity.getValorTotal())
            .frete(entity.getValorFrete())
            .custoProducao(entity.getCustoProducao())
            .personalizacoes(entity.getPersonalizacoes().stream()
                    .map(PersonalizacaoItemPedidoMapperManual::personalizacaoItemPedidoToResponseDTO)
                    .toList())
            .produto(ProdutoMapperManual.toProdutoResumidoResponseDTO(entity.getProduto()))
            .build();
  }


}
