package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.model.Subcategoria;

@Mapper(componentModel = "spring", uses = { SubcategoriaMapper.class })
public interface SubcategoriaMapper {
    SubcategoriaMapper INSTANCE = Mappers.getMapper(SubcategoriaMapper.class);

    SubcategoriaResponseDTO toSubcategoriaResponse(Subcategoria subcategoria);
}
