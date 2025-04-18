package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.ConteudoDinamicoResponseDTO;
import tcatelie.microservice.auth.model.ConteudoDinamico;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {ConteudoDinamicoMapper.class})
public interface ConteudoDinamicoMapper {

  ConteudoDinamicoMapper INSTANCE = Mappers.getMapper(ConteudoDinamicoMapper.class);

  @Mapping(target = "dataCriacao", source = "dataCriacao", qualifiedByName = "localDateTimeToString")
  @Mapping(target = "dataAtualizacao", source = "dataAtualizacao", qualifiedByName = "localDateTimeToString")
  ConteudoDinamicoResponseDTO toResponseDTO(ConteudoDinamico bannersMapper);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
