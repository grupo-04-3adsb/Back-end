package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Personalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personalizacao")
    private Integer idPersonalizacao;

    @Column(name = "nome_personalizacao")
    private String nomePersonalizacao;
    
    @Column(name = "tipo_personalizacao")
    private String tipoPersonalizacao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    @OneToMany(mappedBy = "personalizacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoPersonalizacao> opcoes;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    @PrePersist
    protected void onCreate() {
        this.dthrCadastro = LocalDateTime.now();
        this.dthrAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dthrAtualizacao = LocalDateTime.now();
    }
}
