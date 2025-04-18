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
import tcatelie.microservice.auth.enums.RedeSocial;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "depoimento")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Depoimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "depoimento_id")
  private Long id;

  @Column(name = "depoimento_nome")
  private String nome;

  @Column(name = "depoimento_descricao", length = 700)
  private String descricao;

  @Column(name = "depoimento_rede_social")
  @Enumerated(EnumType.STRING)
  private RedeSocial redeSocial;

  @Column(name = "depoimento_ativo")
  private Boolean ativo;

  @Column(name = "depoimento_data_usuario")
  private LocalDateTime dataUsuario;

  @Column(name = "depoimento_imagem")
  private String imagem;

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
