package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import tcatelie.microservice.auth.dto.response.BannerResponseDTO;
import tcatelie.microservice.auth.model.Banner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {BannersMapper.class})
public interface BannersMapper {

  BannersMapper INSTANCE = Mappers.getMapper(BannersMapper.class);

  @Mapping(target = "dataCriacao", source = "dataCriacao", qualifiedByName = "localDateTimeToString")
  @Mapping(target = "dataAtualizacao", source = "dataAtualizacao", qualifiedByName = "localDateTimeToString")
  BannerResponseDTO toResponseDTO(Banner bannersMapper);

  @Named("localDateTimeToString")
  default String localDateTimeToString(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
    return dateTime.format(formatter);
  }
}
