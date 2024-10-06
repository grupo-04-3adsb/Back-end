package tcatelie.microservice.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Schema(description = "DTO para realizar cadastro do usuário.")
public class RegisterDTO {

    @NotBlank
    @Size(min = 3)
    @Schema(description = "Nome completo do usuário. Deve ter pelo menos 3 caracteres.", example = "Cláudio Araújo")
    private String nome;

    @NotBlank
    @CPF(message = "CPF Inválido")
    @Schema(description = "CPF do usuário. Deve ser um CPF válido.", example = "123.456.789-09")
    private String cpf;

    @NotBlank
    @Schema(description = "Número de telefone do usuário.", example = "(11) 98765-4321")
    private String telefone;

    @NotBlank
    @Email
    @Schema(description = "Endereço de e-mail do usuário. Deve estar em formato válido.", example = "claudio@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    @Schema(description = "Senha do usuário. Deve conter pelo menos 8 caracteres, incluindo um número, uma letra minúscula, uma letra maiúscula e um caractere especial.", example = "#Gf123456")
    private String senha;

    @NotNull
    @Schema(description = "Papel do usuário na aplicação. Não pode ser nulo.", example = "ADMIN")
    private UserRole role;

    @NotNull
    @Schema(description = "Gênero do usuário. Não pode ser nulo.", example = "MASCULINO")
    private Genero genero;

    @Schema(description = "URL da imagem do perfil do usuário. Este campo é opcional.", example = "http://img.png")
    private String imgUrl;

    @Schema(description = "Status do usuário. Este campo é opcional.")
    private Status status;

    @NotNull
    @Schema(description = "Data de nascimento do usuário. Não pode ser nula.", example = "2005-01-07")
    private LocalDate dataNascimento;

    @Schema(description = "Lista de endereços do usuário. Este campo é opcional.")
    private List<EnderecoRequestDTO> enderecos;

    public RegisterDTO(String nome, String cpf, String telefone, String email, String senha, UserRole role,
                       Genero genero, String imgUrl, Status status, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.role = role;
        this.genero = genero;
        this.imgUrl = imgUrl;
        this.status = status;
        this.dataNascimento = dataNascimento;
    }

    public boolean isMaiorDeIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears() >= 18;
    }

    public void validarIdade() {
        if (!isMaiorDeIdade()) {
            throw new IllegalArgumentException("O usuário deve ser maior de idade");
        }
    }
}
