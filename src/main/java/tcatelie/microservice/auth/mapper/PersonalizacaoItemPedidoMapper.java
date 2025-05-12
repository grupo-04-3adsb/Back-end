package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.response.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.mapper.helper.ImageUrlMapperHelper;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

@Mapper(componentModel = "spring", uses = {ImageUrlMapperHelper.class})
@Component
public interface PersonalizacaoItemPedidoMapper {

    PersonalizacaoItemPedidoMapper INSTANCE = Mappers.getMapper(PersonalizacaoItemPedidoMapper.class);

    @Mapping(source = "descricaoPersonalizacao", target = "descricaoPersonalizacao", qualifiedByName = "formatImageUrl")
    PersonalizacaoItemPedidoResponseDTO personalizacaoItemPedidoToResponseDTO(PersonalizacaoItemPedido personalizacaoItemPedido);
}
