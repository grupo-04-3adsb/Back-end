package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personalizacao")
    private Integer idPersonalizacao;

    @Column(name = "nome_personalizacao")
    private String nomePersonalizacao;

    @Column(name = "tipo_personalizacao")
    private String tipoPersonalizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    @OneToMany(mappedBy = "personalizacao", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OpcaoPersonalizacao> opcoes;

    @OneToMany(mappedBy = "personalizacao", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PersonalizacaoItemPedido> personalizacaoItemPedido;

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

