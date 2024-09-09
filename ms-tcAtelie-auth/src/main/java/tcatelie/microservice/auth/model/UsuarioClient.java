package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.util.converters.GeneroConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuario_client") // Nome da tabela
public class UsuarioClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Gerar UUID manualmente
    @Column(name = "id_usuario")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Convert(converter = GeneroConverter.class)
    @Column(name = "genero")
    private Genero genero;

    @OneToMany(mappedBy = "usuarioClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> listaEnderecos;

    @ElementCollection
    @CollectionTable(name = "usuario_client_cargos", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "cargo")
    private List<String> cargos;

    @Column(name = "url_img")
    private String urlImg;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "numero_telefone")
    private String numeroTelefone;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "cpf")
    private String cpf;
}
