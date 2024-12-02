package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @Schema(description = "Id do pedido", example = "1")
    private Integer idPedido;

    @Schema(description = "Status da situação do pedido", example = "PENDENTE")
    private String statusPedido;

    @Schema(description = "Status do pedido", example = "Concluído")
    private boolean concluido;

    @Schema(description = "Ids dos responsáveis", example = "[1, 2, 3]")
    private List<Integer> idsResponsaveis;

    @Schema(description = "Data de quando o pedido foi realizado", example = "18/11/2024")
    private String dataPedido;

    @Schema(name = "nome cliente", example = "Clara")
    private String cliente;

    @Schema(description = "Valor do frete do pedido", example = "100.00")
    private Double valorFrete;

    @Schema(name = "Itens no pedido")
    private List<ItemPedidoRequestDTO> itens;

    @Schema(name = "Endereço de entrega", implementation = EnderecoRequestDTO.class)
    private EnderecoRequestDTO enderecoEntrega;


}
