package tcatelie.microservice.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleAuthDTO {

    @NotBlank()
    private String sub;

    @NotBlank()
    @Size()
    private String name;

    @NotBlank()
    @Size()
    private String givenName;

    @NotBlank()
    @Size()
    private String familyName;

    @Email()
    @NotBlank()
    private String email;

    @NotNull()
    private Boolean emailVerified;

    private String picture;
}