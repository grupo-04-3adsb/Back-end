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
@Table(name = "pagina_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PaginaInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pagina_info_id")
  private Long id;

  @Column(name = "pagina_info_titulo")
  private String titulo;

  @Column(name = "pagina_info_descricao")
  private String descricao;

  @Column(name = "pagina_info_destino")
  private String destino;

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

  @OneToMany(mappedBy = "paginaInfo", fetch = FetchType.EAGER)
  private List<Banner> banners;

  @OneToMany(mappedBy = "paginaInfo", fetch = FetchType.EAGER)
  private List<ConteudoDinamico> conteudosDinamicos;
}
