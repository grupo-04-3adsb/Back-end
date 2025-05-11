package tcatelie.microservice.auth.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoFiltroDTO extends PageFilter {

  private String cep;
  private String rua;
  private String numero;
  private String complemento;
  private String bairro;
  private String cidade;
  private String estado;
  private Integer idUsuario;
}
