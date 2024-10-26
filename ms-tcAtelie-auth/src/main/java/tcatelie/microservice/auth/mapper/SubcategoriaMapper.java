package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.model.Subcategoria;

@Mapper(componentModel = "spring", uses = { SubcategoriaMapper.class })
public interface SubcategoriaMapper {
    SubcategoriaMapper INSTANCE = Mappers.getMapper(SubcategoriaMapper.class);

    @Mapping(source = "nomeSubcategoria", target = "nome")
    Subcategoria toSubcategoria(SubcategoriaRequestDTO dto);

    @Mapping(source = "id", target = "idSubcategoria")
    @Mapping(source = "nome", target = "nomeSubcategoria")
    SubcategoriaResponseDTO toSubcategoriaResponse(Subcategoria subcategoria);
}
