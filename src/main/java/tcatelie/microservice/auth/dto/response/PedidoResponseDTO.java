package tcatelie.microservice.auth.dto;

import lombok.*;

import java.util.List;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.dto.response.ResponsavelResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoResponseDTO {
    private Integer id;
    private Double valorTotal;
    private Double valorDesconto;
    private Double valorFrete;
    private Double totalCustoProducao;
    private Integer parcelas;
    private String formaPgto;
    private String status;
    private String observacao;
    private String dataPedido;
    private String dataEntrega;
    private String dataPagamento;
    private String dataCancelamento;
    private UsuarioResponseDTO cliente;
    private EnderecoResponseDTO enderecoEntrega;
    private List<ItemPedidoResponseDTO> itens;
    private List<ResponsavelResponseDTO> responsaveis;
}
