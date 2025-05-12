package tcatelie.microservice.auth.dto.response.pedido;

import lombok.Builder;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.dto.response.item_pedido.ItemPedidoResumidoResponseDTO;
import tcatelie.microservice.auth.dto.response.produto.ProdutoResumidoResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PedidoDetalhadoResponseDTO(
        Integer id,
        String nomeUsuario,
        Double valorTotal,
        Double valorFrete,
        String formaPgto,
        StatusPedido status,
        String observacao,
        LocalDateTime dataPedido,
        String codigoRastreio,
        EnderecoResponseDTO enderecoEntrega,
        List<ItemPedidoResumidoResponseDTO> itens,
        UsuarioResponseDTO comprador,
        List<ResponsavelResponseDTO> responsaveis
) {
}
