package tcatelie.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.TipoParametro;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametroGeralRequestDTO {

  @NotBlank(message = "O nome do parâmetro é obrigatório")
  @Pattern(regexp = "^[A-Z_]+$", message = "O nome do parâmetro deve conter apenas letras maiúsculas e o caractere de sublinhado")
  private String nome;

  @NotBlank(message = "O valor do parâmetro é obrigatório")
  private String valor;

  private String descricao;

  @NotNull(message = "O tipo do parâmetro é obrigatório")
  private TipoParametro tipo;
}
