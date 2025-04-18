package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponseDTO {

  private Long id;

  private String titulo;

  private String descricao;

  private String buttonText;

  private String buttonLink;

  private String imagem;

  private Boolean ativo;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;
}
