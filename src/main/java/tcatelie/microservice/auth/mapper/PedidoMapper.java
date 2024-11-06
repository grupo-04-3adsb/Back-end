package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.model.Pedido;

@Mapper(componentModel = "spring", uses = {ItemPedidoMapper.class})
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
    @Mapping(source = "dataPedido", target = "dataPedido")
    @Mapping(source = "dataEntrega", target = "dataEntrega")
    @Mapping(source = "dataPagamento", target = "dataPagamento")
    @Mapping(source = "dataCancelamento", target = "dataCancelamento")
    @Mapping(source = "itens", target = "itens")
    PedidoResponseDTO pedidoToPedidoResponseDTO(Pedido pedido);
}
