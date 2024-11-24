package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@Builder
@Schema(description = "DTO para atualizar informações do usuário.")
public class UpdateUserDTO {
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

    @NotNull
    @Schema(description = "Data de nascimento do usuário. Não pode ser nula.", example = "2005-01-07")
    private LocalDate dataNascimento;

    @NotNull
    @Schema(description = "Gênero do usuário. Não pode ser nulo.", example = "MASCULINO")
    private Genero genero;

    @Schema(description = "URL da imagem do perfil do usuário. Este campo é opcional.", example = "http://img.png")
    private String imgUrl;

    @Schema(description = "Status do usuário. Este campo é opcional.")
    private Status status;

    @Schema(description = "Papel do usuário na aplicação. Não pode ser nulo.", example = "ADMIN")
    private UserRole role;


    public UpdateUserDTO(String nome, String cpf, String telefone, String senha, UserRole role,
                         Genero genero, String imgUrl, Status status, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public UpdateUserDTO(String nome, String cpf, String telefone, LocalDate dataNascimento, Genero genero, String imgUrl, Status status, UserRole role) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.imgUrl = imgUrl;
        this.status = status;
        this.role = role;
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
