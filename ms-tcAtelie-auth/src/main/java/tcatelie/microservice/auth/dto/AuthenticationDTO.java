package tcatelie.microservice.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "DTO para realizar autenticação do usuário.")
public class AuthenticationDTO {

	@NotBlank
	@Schema(description = "Email do usuário para login", example = "claudio@gmail.com")
	private String email;

	@NotBlank
	@Size(min = 8)
	@Schema(description = "Senha do usuário para login", example = "#Gf123456")
	private String senha;

}
