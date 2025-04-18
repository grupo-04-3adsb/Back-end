package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoresResponseDTO {

  private Long id;

  private String titulo;

  private String descricao;

  private Boolean ativo;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;
}
