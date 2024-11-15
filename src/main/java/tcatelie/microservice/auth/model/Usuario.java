package tcatelie.microservice.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "USUARIO")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "NOME_USUARIO")
    private String nome;

    @Column(name = "EMAIL_USUARIO", unique = true)
    private String email;

    @Column(name = "SENHA_USUARIO")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "CARGO_USUARIO")
    private UserRole role;

    @Column(name = "TELEFONE_USUARIO")
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_USUARIO")
    private Status status;

    @Column(name = "DATA_CADASTRO_USUARIO")
    private LocalDateTime dthrCadastro;

    @Column(name = "DATA_ATUALIZACAO_USUARIO")
    private LocalDateTime dthrAtualizacao;

    @Column(name = "CPF_USUARIO", unique = true)
    private String cpf;

    @Column(name = "GENERO_USUARIO")
    private Genero genero;

    @Column(name = "URL_IMG_USUARIO")
    private String urlImgUsuario;

    @Column(name = "DATA_NASCIMENTO_USUARIO")
    private LocalDate dataNascimento;

    @Column(name = "ID_GOOGLE")
    private String idGoogle;

    @Column(name = "id_img_drive")
    private String idImgDrive;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public Usuario(String telefone, UserRole role, String senha, String email, String nome, String cpf, Genero genero, String urlImgUsuario, LocalDate dataNascimento) {
        this.telefone = telefone;
        this.role = role;
        this.senha = senha;
        this.email = email;
        this.nome = nome;
        this.cpf = cpf;
        this.status = Status.HABILITADO;
        this.genero = genero;
        this.urlImgUsuario = urlImgUsuario;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(Integer idUsuario, String nome, String email, String senha, UserRole role, String telefone, Status status, LocalDateTime dthrCadastro, LocalDateTime dthrAtualizacao, String cpf, Genero genero, String urlImgUsuario, LocalDate dataNascimento) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.telefone = telefone;
        this.status = status;
        this.dthrCadastro = dthrCadastro;
        this.dthrAtualizacao = dthrAtualizacao;
        this.cpf = cpf;
        this.genero = genero;
        this.urlImgUsuario = urlImgUsuario;
        this.dataNascimento = dataNascimento;
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.HABILITADO);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
