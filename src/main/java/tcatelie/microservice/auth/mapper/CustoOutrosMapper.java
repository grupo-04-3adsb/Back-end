package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.CustoOutrosRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.model.CustoOutros;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface CustoOutrosMapper {

    CustoOutrosMapper INSTANCE = Mappers.getMapper(CustoOutrosMapper.class);

    @Mapping(source = "id", target = "idCustoOutros")
    CustoOutros toEntity(CustoOutrosRequestDTO requestDTO);

    @Mapping(source = "idCustoOutros", target = "id")
    @Mapping(source = "dataHoraAtualizacao", target = "dataHoraAtualizacao", qualifiedByName = "localDateTimeProdutoToString")
    @Mapping(source = "dataHoraCriacao", target = "dataHoraCriacao", qualifiedByName = "localDateTimeProdutoToString")
    CustoOutrosResponseDTO toResponseDTO(CustoOutros entity);

    @Named("localDateTimeProdutoToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }
}
