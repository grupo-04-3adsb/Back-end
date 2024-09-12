package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Logradouro;
import tcatelie.microservice.auth.util.converters.LogradouroConverter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cep")
    private String cep;

    @Column(name = "pais")
    private String pais;

    @Column(name = "nome_contato")
    private String nomeContato;

    @Column(name = "telefone_contato")
    private String telefoneContato;

    @Column(name = "email_contato")
    private String emailContato;

    @Column(name = "instrucoes_entrega")
    private String instrucoesEntrega;

    @Column(name = "endereco_padrao")
    private boolean enderecoPadrao;

    @Convert(converter = LogradouroConverter.class)
    @Column(name = "tipo")
    private Logradouro tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_client_id")
    private Usuario usuario;
}
