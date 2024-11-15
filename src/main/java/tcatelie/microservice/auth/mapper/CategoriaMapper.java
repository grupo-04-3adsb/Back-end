package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.CategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.CategoriaResponseDTO;
import tcatelie.microservice.auth.model.Categoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    @Mapping(source = "idCategoria", target = "idCategoria")
    @Mapping(source = "nomeCategoria", target = "nomeCategoria")
    @Mapping(source = "subcategorias", target = "subcategorias")
    @Mapping(source = "dthrCadastro", target = "dthrCadastro", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "dthrAtualizacao", target = "dthrAtualizacao", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "qtdProdutosCategoria", ignore = true)
    @Mapping(target = "qtdSubcategorias", ignore = true)
    CategoriaResponseDTO toCategoriaResponse(Categoria categoria);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "subcategorias", ignore = true)
    @Mapping(target = "nomeCategoria", source = "nome")
    Categoria toCategoria(CategoriaRequestDTO categoriaRequestDTO);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }
}