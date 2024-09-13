package tcatelie.microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Logradouro;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequestDTO {

	@NotBlank
	private String rua;

	@NotBlank
	private String numero;

	private String complemento;

	@NotBlank
	private String bairro;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;

	@NotBlank
	@Pattern(regexp = "\\d{5}-\\d{3}")
	private String cep;

	@NotBlank
	private String pais;

	private String instrucaoEntrega;

	@NotNull
	private Boolean enderecoPadrao;

	@NotNull
	private Logradouro logradouro;

}
