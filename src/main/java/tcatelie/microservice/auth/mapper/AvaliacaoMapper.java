package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.request.AvaliacaoRequestDTO;
import tcatelie.microservice.auth.dto.response.AvaliacaoResponseDTO;
import tcatelie.microservice.auth.model.Avaliacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
@Component
public interface AvaliacaoMapper {

    AvaliacaoMapper INSTANCE = Mappers.getMapper(AvaliacaoMapper.class);

    @Mapping(source = "usuarioId", target = "usuario.idUsuario")
    @Mapping(source = "produtoId", target = "produto.id")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "nota", target = "notaAvaliacao")
    @Mapping(source = "aprovada", target = "avaliacaoAprovada")
    Avaliacao toAvaliacao(AvaliacaoRequestDTO requestDTO);

    @Mapping(source = "usuario.idUsuario", target = "usuarioId")
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "notaAvaliacao", target = "nota")
    @Mapping(source = "avaliacaoAprovada", target = "aprovada")
    @Mapping(source = "dataHoraAvaliacao", target = "dataAvaliacao", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "produto.nome", target = "nomeProduto")
    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Mapping(source = "idAvaliacao", target = "id")
    AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao);

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd/MM/yyyy");
        return dateTime.format(formatter);
    }
}
