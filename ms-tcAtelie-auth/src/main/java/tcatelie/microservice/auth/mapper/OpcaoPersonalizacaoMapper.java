package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.OpcaoPersonalizacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.OpcaoPersonalizacaoResponseDTO;
import tcatelie.microservice.auth.model.OpcaoPersonalizacao;

@Mapper(componentModel = "spring")
@Component
public interface OpcaoPersonalizacaoMapper {

    OpcaoPersonalizacaoMapper INSTANCE = Mappers.getMapper(OpcaoPersonalizacaoMapper.class);

    @Mapping(target = "urlImagemOpcao", source = "urlImagemOpcao")
    @Mapping(target = "acrescimoOpcao", source = "acrescimoOpcao")
    @Mapping(target = "descricao", source = "descricaoProduto")
    @Mapping(target = "idImgDrive", source = "idDrive")
    @Mapping(target = "personalizacao", ignore = true)
    @Mapping(target = "idOpcaoPersonalizacao", source = "idOpcao")
    OpcaoPersonalizacao toOpcaoPersonalizacao(OpcaoPersonalizacaoRequestDTO opcaoRequestDTO);

    @Mapping(target = "acrescimo", source = "acrescimoOpcao")
    @Mapping(target = "descricaoOpcao", source = "descricao")
    @Mapping(target = "dthrCriacao", source = "dthrCadastro")
    @Mapping(target = "dthrAtualizacao", source = "dthrAtualizacao")
    @Mapping(source = "idImgDrive", target = "idImgDrive")
    @Mapping(source = "idOpcaoPersonalizacao", target = "idOpcao")
    OpcaoPersonalizacaoResponseDTO toOpcaoPersonalizacaoResponseDTO(OpcaoPersonalizacao opcaoPersonalizacao);
}
