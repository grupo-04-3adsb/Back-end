package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.request.FAQRequestDTO;
import tcatelie.microservice.auth.dto.response.FAQResponseDTO;
import tcatelie.microservice.auth.model.Faq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {FAQMapper.class})
public interface FAQMapper {

  FAQMapper INSTANCE = Mappers.getMapper(FAQMapper.class);

  Faq toEntity(FAQRequestDTO requestDTO);

  @Mapping(source = "dataCriacao", target = "dataCriacao", qualifiedByName = "localDateTimeToString")
  @Mapping(source = "dataAtualizacao", target = "dataAtualizacao", qualifiedByName = "localDateTimeToString")
  FAQResponseDTO toResponseDTO(Faq entity);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
