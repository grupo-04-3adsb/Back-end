package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.model.Banner;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginaInfoResponseDTO {

  private Long id;

  private String titulo;

  private String descricao;

  private String dataCriacao;

  private String dataAtualizacao;

  private String usuarioCriacao;

  private String usuarioAtualizacao;

  private List<BannerResponseDTO> banners;

  private List<ConteudoDinamicoResponseDTO> conteudosDinamicos;
}
