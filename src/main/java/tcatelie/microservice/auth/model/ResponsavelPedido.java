package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@IdClass(ResponsavelPedidoId.class)
@Table(name = "responsavel_pedido")
public class ResponsavelPedido {

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_responsavel")
    private Usuario responsavel;

    @Id
    @ManyToOne
    @JoinColumn(name = "fk_pedido")
    private Pedido pedido;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dataHoraCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dataHoraAtualizacao;

    @PrePersist
    public void prePersist() {
        dataHoraCadastro = LocalDateTime.now();
        dataHoraAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        dataHoraAtualizacao = LocalDateTime.now();
    }
}
