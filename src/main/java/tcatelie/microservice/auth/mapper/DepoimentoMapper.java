package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.DepoimentosResponseDTO;
import tcatelie.microservice.auth.model.Depoimento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {DepoimentoMapper.class})
public interface DepoimentoMapper {

  DepoimentoMapper INSTANCE = Mappers.getMapper(DepoimentoMapper.class);

  @Mapping(source = "dataUsuario", target = "dataUsuario", qualifiedByName = "localDateTimeToString")
  @Mapping(source = "dataCriacao", target = "dataCriacao", qualifiedByName = "localDateTimeToString")
  @Mapping(source = "dataAtualizacao", target = "dataAtualizacao", qualifiedByName = "localDateTimeToString")
  DepoimentosResponseDTO toDTO(Depoimento depoimento);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
