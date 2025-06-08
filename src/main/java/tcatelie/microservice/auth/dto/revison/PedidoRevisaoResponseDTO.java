package tcatelie.microservice.auth.dto.revison;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.dto.response.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.EnderecoResponseDTO;
import tcatelie.microservice.auth.dto.response.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.enums.OrigemPedido;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.enums.UserRole;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRevisaoResponseDTO {

  private Double valorVenda;
  private Double custoMaterial;
  private Double lucroReais;
  private Double lucroPercentual;
  private Double valorFreteTotal;
  private StatusPedido statusPedido;
  private String dataPedido;
  private String dataConclusao;
  private String dataEnvio;
  private String dataEntrega;
  private String nomeCliente;
  private String telefoneCliente;
  private String emailCliente;
  private EnderecoResponseDTO enderecoEntrega;
  private List<ItemPedidoResponseDTO> itens;
  private String observacao;
  private String formaPagamento;
  private OrigemPedido origemPedido;
  private UserRole tipoUsuario;
}
