package tcatelie.microservice.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationDTO{

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String senha;

}
