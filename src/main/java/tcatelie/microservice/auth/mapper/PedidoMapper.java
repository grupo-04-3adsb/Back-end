package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;
import tcatelie.microservice.auth.model.ResponsavelPedido;
import tcatelie.microservice.auth.model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemPedidoMapper.class, UsuarioMapper.class, ProdutoMapper.class, EnderecoMapper.class})
@Component
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(source = "valorTotal", target = "valorTotal")
    @Mapping(source = "valorDesconto", target = "valorDesconto")
    @Mapping(source = "valorFrete", target = "valorFrete")
    @Mapping(source = "parcelas", target = "parcelas")
    @Mapping(source = "formaPgto", target = "formaPgto")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "observacao", target = "observacao")
    @Mapping(source = "dataPedido", target = "dataPedido", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "dataEntrega", target = "dataEntrega", qualifiedByName = "localDateTimeToStringDateFormat")
    @Mapping(source = "dataPagamento", target = "dataPagamento", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "dataCancelamento", target = "dataCancelamento", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "itens", target = "itens")
    @Mapping(source = "usuario", target = "cliente")
    @Mapping(target = "cliente.senha", ignore = true)
    @Mapping(target = "enderecoEntrega", source = "enderecoEntrega")
    @Mapping(source = "responsaveis", target = "responsaveis", qualifiedByName = "responsavelToResponsavelResponseDTO")
    PedidoResponseDTO pedidoToPedidoResponseDTO(Pedido pedido);

    @Named("responsavelToResponsavelResponseDTO")
    default List<ResponsavelResponseDTO> responsavelToResponsavelResponseDTO(List<ResponsavelPedido> responsaveis) {
        UsuarioMapper usuarioMapper = Mappers.getMapper(UsuarioMapper.class);
        return responsaveis.stream().map(responsavel -> {
            Usuario usuarioResponsavel = responsavel.getResponsavel();
            return usuarioMapper.toResponsavelResponseDTO(usuarioResponsavel);
        }).toList();
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    @Named("localDateTimeToStringDateFormat")
    default String localDateTimeToStringDateFormat(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

}
