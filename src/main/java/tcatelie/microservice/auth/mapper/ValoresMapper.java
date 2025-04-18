package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.ValoresResponseDTO;
import tcatelie.microservice.auth.model.Valores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {ValoresMapper.class})
public interface ValoresMapper {

  ValoresMapper INSTANCE = Mappers.getMapper(ValoresMapper.class);

  @Mapping(source = "dataCriacao", target = "dataCriacao", qualifiedByName = "localDateTimeToString")
  @Mapping(source = "dataAtualizacao", target = "dataAtualizacao", qualifiedByName = "localDateTimeToString")
  ValoresResponseDTO toDTO(Valores entity);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
