package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.model.Categoria;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    @Mapping(source = "idCategoria", target = "idCategoria")
    @Mapping(source = "nomeCategoria", target = "nomeCategoria")
    @Mapping(source = "subcategorias", target = "subcategorias")
    CategoriaResponseDTO toCategoriaResponse(Categoria categoria);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    Categoria toCategoria(CategoriaRequestDTO categoriaRequestDTO);
}