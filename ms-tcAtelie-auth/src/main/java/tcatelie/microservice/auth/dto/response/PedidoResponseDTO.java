package tcatelie.microservice.auth.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDTO {
    private Integer id;
    private Double valorTotal;
    private Double valorDesconto;
    private Double valorFrete;
    private Integer parcelas;
    private String formaPgto;
    private String status;
    private String observacao;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataCancelamento;
    private List<ItemPedidoResponseDTO> itens;
}
