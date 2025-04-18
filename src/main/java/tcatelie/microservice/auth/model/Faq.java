package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "faq")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Faq {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String resposta;

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
