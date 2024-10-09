package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.PersonalizacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoResponseDTO;
import tcatelie.microservice.auth.model.Personalizacao;

@Mapper(componentModel = "spring", uses = OpcaoPersonalizacaoMapper.class)
@Component
public interface PersonalizacaoMapper {

    PersonalizacaoMapper INSTANCE = Mappers.getMapper(PersonalizacaoMapper.class);

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "dthrCadastro", ignore = true)
    @Mapping(target = "dthrAtualizacao", ignore = true)
    @Mapping(target = "opcoes", source = "opcoes")
    Personalizacao toPersonalizacao(PersonalizacaoRequestDTO personalizacaoRequestDTO);

    @Mapping(source = "idPersonalizacao", target = "idPersonalizacao")
    @Mapping(source = "nomePersonalizacao", target = "nomePersonalizacao")
    @Mapping(source = "tipoPersonalizacao", target = "tipoPersonalizacao")
    @Mapping(source = "dthrCadastro", target = "dthrCriacao")
    @Mapping(target = "opcoes", source = "opcoes")
    PersonalizacaoResponseDTO toPersonalizacaoResponseDTO(Personalizacao personalizacao);
}
