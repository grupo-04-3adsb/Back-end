package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConteudoDinamicoResponseDTO {

  private Long id;

  private String titulo;

  private String descricao;

  private String buttonText;

  private String buttonLink;

  private Boolean ativo;

  private String html;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;

  private List<String> imagens;
}
