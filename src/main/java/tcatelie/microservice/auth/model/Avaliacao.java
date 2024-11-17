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
@Table(name = "AVALIACAO")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @Column(name = "titulo", length = 45)
    private String titulo;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "nota_avaliacao")
    private Integer notaAvaliacao;

    @Column(name = "avaliacao_aprovada")
    private Boolean avaliacaoAprovada;

    @Column(name = "data_hora_avaliacao")
    private LocalDateTime dataHoraAvaliacao;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dataHoraAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        this.dataHoraAvaliacao = LocalDateTime.now();
        this.dataHoraAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataHoraAtualizacao = LocalDateTime.now();
    }
}

