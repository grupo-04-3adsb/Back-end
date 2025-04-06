package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FAQResponseDTO {

  private Long id;

  private String titulo;

  private String resposta;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;

}
