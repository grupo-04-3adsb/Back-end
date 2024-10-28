package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.model.Pedido;

@Mapper
public interface PedidoMapper {
    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    PedidoResponseDTO pedidoToPedidoResponseDTO(Pedido pedido);
}
