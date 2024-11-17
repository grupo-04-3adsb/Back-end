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
@Table(name = "custo_outros")
public class CustoOutros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custo_outros")
    private Integer idCustoOutros;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dataHoraAtualizacao;

    @Column(name = "data_hora_criacao")
    private LocalDateTime dataHoraCriacao;

    @PreUpdate
    public void preUpdate() {
        dataHoraAtualizacao = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        dataHoraCriacao = LocalDateTime.now();
        dataHoraAtualizacao = LocalDateTime.now();
    }
}
