package tcatelie.microservice.auth.dto;

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
public class RegisterDTO {

	@NotBlank
	@Size(min = 3)
	private String nome;

	@NotBlank
	@CPF(message = "CPF Inválido")
	private String cpf;

	@NotBlank
	private String telefone;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$")
	private String senha;

	@NotNull
	private UserRole role;

	@NotNull
	private Genero genero;

	private String imgUrl;

	private Status status;

	@NotNull
	private LocalDate dataNascimento;

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
