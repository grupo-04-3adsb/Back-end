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
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private Integer idSubcategoria;

    @Column(name = "nome_subcategoria")
    private String nomeSubcategoria;

    @Column(name = "descricao_subcategoria")
    private String descricaoSubcategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "subcategoria")
    private List<Produto> produtos;

    @Column(name = "codigo_cor")
    private String codigoCor;

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
