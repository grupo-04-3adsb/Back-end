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

@Entity
@Table(name = "banner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Banner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "banner_id")
  private Long id;

  @Column(name = "banner_titulo")
  private String titulo;

  @Column(name = "banner_descricao")
  private String descricao;

  @Column(name = "banner_button_text")
  private String buttonText;

  @Column(name = "banner_button_link")
  private String buttonLink;

  @Column(name = "banner_imagem")
  private String imagem;

  @Column(name = "banner_ativo")
  private Boolean ativo;

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

  @ManyToOne
  @JoinColumn(name = "pagina_info_id")
  private PaginaInfo paginaInfo;
}
