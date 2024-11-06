package tcatelie.microservice.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Request DTO para autenticação via Google")
public class GoogleAuthDTO {

    @NotBlank
    @Schema(description = "Identificador único do usuário no Google", example = "1234567890")
    private String sub;

    @NotBlank
    @Size(min = 1, max = 100)
    @Schema(description = "Nome completo do usuário", example = "Cláudio Araújo")
    private String name;

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Primeiro nome do usuário", example = "Cláudio")
    private String givenName;

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(description = "Último nome do usuário", example = "Araújo")
    private String familyName;

    @Email
    @NotBlank
    @Schema(description = "Email do usuário", example = "john.doe@gmail.com")
    private String email;

    @NotNull
    @Schema(description = "Indica se o email foi verificado pelo Google", example = "true")
    private Boolean emailVerified;

    @Schema(description = "URL da foto de perfil do usuário", example = "https://google.com/claudio.jpg")
    private String picture;
}
