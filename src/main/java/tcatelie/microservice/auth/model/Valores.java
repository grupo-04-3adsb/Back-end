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

@Table(name = "valores")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Valores {

  @Id
  @Column(name = "valores_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "valores_titulo")
  private String titulo;

  @Column(name = "valores_descricao", length = 700)
  private String descricao;

  @Column(name = "valores_ativo")
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
}
