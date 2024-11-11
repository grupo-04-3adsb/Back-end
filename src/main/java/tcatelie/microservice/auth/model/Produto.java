package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.observer.Observer;
import tcatelie.microservice.auth.repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Produto implements Observer {

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

    @Column(name = "id_img_drive")
    private String idImgDrive;

    @Column(name = "personalizavel")
    private boolean personalizavel;

    @Column(name = "personalizacao_obrigatoria")
    private boolean isPersonalizacaoObrigatoria;

    @Column(name = "data_hora_cadastro")
    private LocalDateTime dthrCadastro;

    @Column(name = "data_hora_atualizacao")
    private LocalDateTime dthrAtualizacao;

    @Column(name = "produto_ativo")
    private Boolean produtoAtivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subcategoria", nullable = false)
    private Subcategoria subcategoria;

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ImagensProduto> imagensAdicionais = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personalizacao> personalizacoes = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MaterialProduto> materiaisProduto = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    @Transient
    private ProdutoRepository repository;

    @PrePersist
    protected void onCreate() {
        this.dthrCadastro = LocalDateTime.now();
        this.dthrAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dthrAtualizacao = LocalDateTime.now();
    }

    public void aplicarMargemLucro() {

        margemLucro = calcularMargemLucro();

        if (repository != null) {
            repository.save(this);
        }
    }

    public Double calcularMargemLucro() {
        if (preco == null || preco <= 0) {
            return 0.0;
        }

        double custoTotal = 0.0;
        for (MaterialProduto materialProduto : materiaisProduto) {
            Material material = materialProduto.getMaterial();
            int quantidadeNecessaria = materialProduto.getQtdMaterialNecessario();
            custoTotal += material.getPrecoUnitario() * quantidadeNecessaria;
        }

        return ((preco - custoTotal) / preco) * 100;
    }

    @Override
    public void update(String message, Produto produto) {

    }

    @Override
    public void update(String message) {

    }
}
