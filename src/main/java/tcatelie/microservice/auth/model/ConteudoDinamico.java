package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "conteudo_dinamico")
public class ConteudoDinamico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "conteudo_dinamico_id")
  private Long id;

  @Column(name = "conteudo_dinamico_titulo")
  private String titulo;

  @Column(name = "conteudo_dinamico_descricao", length = 1000)
  private String descricao;

  @Column(name = "conteudo_dinamico_button_text")
  private String buttonText;

  @Column(name = "conteudo_dinamico_button_link")
  private String buttonLink;

  @Column(name = "conteudo_dinamico_ativo")
  private Boolean ativo;

  @Column(name = "conteudo_dinamico_html")
  private String html;

  @ElementCollection
  @CollectionTable(name = "conteudo_dinamico_imagens", joinColumns = @JoinColumn(name = "conteudo_dinamico_id"))
  @Column(name = "imagem")
  private List<String> imagens;

  @ManyToOne
  @JoinColumn(name = "pagina_info_id")
  private PaginaInfo paginaInfo;

  @Column()
  @CreationTimestamp
  private LocalDateTime dataCriacao;

  @Column()
  @UpdateTimestamp
  private LocalDateTime dataAtualizacao;

  @CreatedBy
  @Column(name = "usuario_criacao")
  private String usuarioCriacao;

  @LastModifiedBy
  @Column(name = "usuario_atualizacao")
  private String usuarioAtualizacao;

}
