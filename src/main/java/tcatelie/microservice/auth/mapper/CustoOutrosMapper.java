package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.CustoOutrosRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.model.CustoOutros;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface CustoOutrosMapper {

    CustoOutrosMapper INSTANCE = Mappers.getMapper(CustoOutrosMapper.class);

    @Mapping(source = "id", target = "idCustoOutros")
    CustoOutros toEntity(CustoOutrosRequestDTO requestDTO);

    @Mapping(source = "idCustoOutros", target = "id")
    CustoOutrosResponseDTO toResponseDTO(CustoOutros entity);
}
