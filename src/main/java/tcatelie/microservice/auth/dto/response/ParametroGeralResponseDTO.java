package tcatelie.microservice.auth.dto.response;

import lombok.*;
import tcatelie.microservice.auth.enums.TipoParametro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametroGeralResponseDTO {

  private String nome;
  private String valor;
  private String descricao;
  private TipoParametro tipo;
  private String usuarioCriacao;
  private String usuarioAtualizacao;
  private String dataHoraCriacao;
  private String dataHoraAtualizacao;
}
