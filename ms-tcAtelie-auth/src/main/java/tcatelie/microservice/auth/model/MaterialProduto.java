package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@IdClass(MaterialProdutoId.class)
public class MaterialProduto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_material")
    private Material material;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    @Column(name = "qtd_material_necessario")
    private Integer qtdMaterialNecessario;


    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    public MaterialProduto(Material material, Produto produto, Integer qtdMaterialNecessario){
        this.material = material;
        this.produto = produto;
        this.qtdMaterialNecessario = qtdMaterialNecessario;
    }

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
