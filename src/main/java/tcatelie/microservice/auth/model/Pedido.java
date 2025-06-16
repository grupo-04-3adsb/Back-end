package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.enums.OrigemPedido;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.util.converters.StatusPedidoConverter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    @Column(name = "total")
    private Double valorTotal;

    @Column(name = "valor_desconto")
    private Double valorDesconto = 0.0;

    @Column(name = "valor_frete")
    private Double valorFrete;

    @Column(name = "num_parcela")
    private Integer parcelas;

    @Column(name = "valor_parcela")
    private Double valorParcela;

    @Column(name = "forma_pagamento")
    private String formaPgto;

    @Column(name = "status")
    @Convert(converter = StatusPedidoConverter.class)
    private StatusPedido status;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_pagamento")
    private String paymentId;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem_pedido")
    private OrigemPedido origemPedido;

    @Column(name = "telefone_cliente")
    private String telefoneCliente;

    @Column(name = "nota_fiscal_url")
    private String notaFiscalUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "fk_endereco_entrega")
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<ItemPedido> itens;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ResponsavelPedido> responsaveis;

    @PrePersist
    protected void onCreate() {
        this.dataPedido = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Double calucularValorCarrinho() {
        return itens.stream().map(ItemPedido::getProduto).mapToDouble(p -> {
            double desconto = p.getDesconto();
            return (p.getPreco() * desconto) / 100;
        }).sum();
    }

    public Double calcularValorPedido() {
        return itens.stream().mapToDouble(i -> {
            double desconto = i.getValorDesconto();
            return (i.getValor() * desconto) / 100;
        }).sum();
    }
}
