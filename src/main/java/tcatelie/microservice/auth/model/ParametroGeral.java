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
import tcatelie.microservice.auth.enums.TipoParametro;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "parametro_geral")
public class ParametroGeral {

  @Id
  @Column(name = "parametro_geral_id")
  private String nome;

  @Column(name = "parametro_geral_valor")
  private String valor;

  @Column(name = "parametro_geral_descricao")
  private String descricao;

  @Column(name = "data_hora_atualizacao")
  @UpdateTimestamp
  private LocalDateTime dataHoraAtualizacao;

  @Column(name = "data_hora_criacao")
  @CreationTimestamp
  private LocalDateTime dataHoraCriacao;

  @Column(name = "parametro_geral_tipo")
  @Enumerated(EnumType.STRING)
  private TipoParametro tipo;

  @CreatedBy
  @Column(name = "usuario_criacao")
  private String usuarioCriacao;

  @LastModifiedBy
  @Column(name = "usuario_atualizacao")
  private String usuarioAtualizacao;

}
