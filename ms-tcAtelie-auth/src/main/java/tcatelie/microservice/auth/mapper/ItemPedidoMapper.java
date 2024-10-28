package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.model.ItemPedido;

@Mapper
public interface ItemPedidoMapper {
    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    ItemPedidoResponseDTO itemPedidoToItemPedidoResponseDTO(ItemPedido itemPedido);
}
