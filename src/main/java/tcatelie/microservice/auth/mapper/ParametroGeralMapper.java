package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.ParametroGeralRequestDTO;
import tcatelie.microservice.auth.dto.response.ParametroGeralResponseDTO;
import tcatelie.microservice.auth.model.ParametroGeral;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface ParametroGeralMapper {

  ParametroGeralMapper INSTANCE = Mappers.getMapper(ParametroGeralMapper.class);

  ParametroGeral toEntity(ParametroGeralRequestDTO requestDTO);

  @Mapping(source = "dataHoraAtualizacao", target = "dataHoraAtualizacao", qualifiedByName = "localDateTimeParametroToString")
  @Mapping(source = "dataHoraCriacao", target = "dataHoraCriacao", qualifiedByName = "localDateTimeParametroToString")
  ParametroGeralResponseDTO toResponseDTO(ParametroGeral entity);

  @Named("localDateTimeParametroToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
