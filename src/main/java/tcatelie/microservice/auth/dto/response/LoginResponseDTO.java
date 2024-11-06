package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Response DTO para resposta do login do usuário.")
public class LoginResponseDTO{

    @Schema(description = "Usuário logado")
    private UsuarioResponseDTO usuario;

    @Schema(description = "Token JWT")
    private String token;
}
