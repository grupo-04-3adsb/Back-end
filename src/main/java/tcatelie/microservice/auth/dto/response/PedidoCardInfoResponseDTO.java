package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCardInfoResponseDTO {

  private Integer id;

  private String nomeUsuario;

  private String emailCliente;

  private String tipoCliente;

  private Double valorTotal;

  private String dataEntrega;

  private String dataPedido;

  private Integer qtdItens;

  private String status;

  private List<ResponsavelResponseDTO> responsaveis;

  private List<String> categorias;

  private List<String> subcategorias;
}
