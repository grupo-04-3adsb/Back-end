package tcatelie.microservice.auth.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import tcatelie.microservice.auth.enums.Genero;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.UserRole;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@Data
public class RegisterDTO {

    @NotBlank
    @Size(min = 3)
    private String nome;


    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}")
    private String telefone;


    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    )
    private String senha;

    @NotNull
    private UserRole role;

    @NotNull
    private Genero genero;

    private String imgUrl;

    private Status status;

    @NotNull
    private LocalDate dataNascimento;

    public boolean isMaiorDeIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears() >= 18;
    }

    public void validarIdade() {
        if (!isMaiorDeIdade()) {
            throw new IllegalArgumentException("O usuário deve ser maior de idade");
        }
    }
}
