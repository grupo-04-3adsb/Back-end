package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.SubcategoriaRequestDTO;
import tcatelie.microservice.auth.dto.response.SubcategoriaResponseDTO;
import tcatelie.microservice.auth.model.Subcategoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {SubcategoriaMapper.class})
public interface SubcategoriaMapper {
    SubcategoriaMapper INSTANCE = Mappers.getMapper(SubcategoriaMapper.class);

    @Mapping(source = "nomeSubcategoria", target = "nomeSubcategoria")
    Subcategoria toSubcategoria(SubcategoriaRequestDTO dto);

    @Mapping(source = "idSubcategoria", target = "idSubcategoria")
    @Mapping(source = "nomeSubcategoria", target = "nomeSubcategoria")
    @Mapping(source = "dthrCadastro", target = "dthrCadastro", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "dthrAtualizacao", target = "dthrAtualizacao", qualifiedByName = "localDateTimeToString")
    @Mapping(target = "qtdProdutosSubcategoria", ignore = true)
    @Mapping(source = "categoria.nomeCategoria",target = "nomeCategoria")
    SubcategoriaResponseDTO toSubcategoriaResponse(Subcategoria subcategoria);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }
}
