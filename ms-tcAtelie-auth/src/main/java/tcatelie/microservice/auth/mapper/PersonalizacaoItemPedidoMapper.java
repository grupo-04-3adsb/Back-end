package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

@Mapper
public interface PersonalizacaoItemPedidoMapper {
    PersonalizacaoItemPedidoMapper INSTANCE = Mappers.getMapper(PersonalizacaoItemPedidoMapper.class);

    PersonalizacaoItemPedidoResponseDTO personalizacaoItemPedidoToResponseDTO(PersonalizacaoItemPedido personalizacaoItemPedido);
}