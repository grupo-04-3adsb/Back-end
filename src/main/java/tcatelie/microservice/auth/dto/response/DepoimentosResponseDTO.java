package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepoimentosResponseDTO {

  private Long id;

  private String nome;

  private String descricao;

  private String redeSocial;

  private Boolean ativo;

  private String imagem;

  private String dataUsuario;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;
}
