package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.pedido.PedidoDetalhadoResponseDTO;
import tcatelie.microservice.auth.model.Pedido;

public class PedidoMapperManual {


    public static PedidoDetalhadoResponseDTO toPedidoDetalhadoResponseDTO(Pedido entity){

        return PedidoDetalhadoResponseDTO.builder()
                .id(entity.getId())
                .nomeUsuario(entity.getNomeUsuario())
                .valorTotal(entity.getValorTotal())
                .valorFrete(entity.getValorFrete())
                .formaPgto(entity.getFormaPgto())
                .status(entity.getStatus())
                .observacao(entity.getObservacao())
                .dataPedido(entity.getDataPedido())
                .codigoRastreio(entity.getCodigoRastreio())
                .enderecoEntrega(EnderecoMapper.INSTANCE.toEnderecoResponseDTO(entity.getEnderecoEntrega()))
                .itens(entity.getItens().stream()
                        .map(ItemPedidoMapperManual::toItemPedidoResumidoResponseDTO)
                        .toList())
                .comprador(UsuarioMapperManual.toUsuarioResponseDTO(entity.getUsuario()))
                .responsaveis(entity.getResponsaveis().stream()
                        .map(ResponsavelPedidoMapper::toResponsavelResponseDTO)
                        .toList())
                .build();
    }

}
