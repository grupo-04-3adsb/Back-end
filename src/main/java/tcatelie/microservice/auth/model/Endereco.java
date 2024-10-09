package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Logradouro;
import tcatelie.microservice.auth.util.converters.LogradouroConverter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ENDERECO")
    private Integer id;

    @Column(name = "RUA")
    private String rua;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "PAIS")
    private String pais;

    @Column(name = "INSTRUCAO_ENTREGA")
    private String instrucaoEntrega;

    @Column(name = "ENDERECO_PADRAO")
    private boolean enderecoPadrao;

    @Convert(converter = LogradouroConverter.class)
    @Column(name = "LOGRADOURO")
    private Logradouro logradouro;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @Column(name = "DATA_CADASTRO_ENDERECO")
    private LocalDateTime dthrCadastro;

    @Column(name = "DATA_ATUALIZACAO_USUARIO")
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
