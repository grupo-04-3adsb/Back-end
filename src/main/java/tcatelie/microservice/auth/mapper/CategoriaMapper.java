package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.model.Categoria;

@Mapper(componentModel = "spring", uses = { CategoriaMapper.class })
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    @Mapping(target = "subcategorias", source = "subcategorias")
    CategoriaResponseDTO toCategoriaResponse(Categoria categoria);

}
