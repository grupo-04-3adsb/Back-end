package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco_venda")
    private Double preco;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "dimensao")
    private String dimensao;

    @Column(name = "desconto")
    private Double desconto;

    @Column(name = "margem_lucro")
    private Double margemLucro;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "url_imagem_principal")
    private String urlImagemPrincipal;

    @Column(name = "personalizavel")
    private boolean personalizavel;

    @Column(name = "personalizacao_obrigatoria")
    private boolean isPersonalizacaoObrigatoria;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subcategoria", nullable = false)
    private Subcategoria subcategoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagensProduto> imagensAdicionais = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personalizacao> personalizacoes = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MaterialProduto> materiaisProduto = new ArrayList<>();

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
