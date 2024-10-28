package tcatelie.microservice.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request DTO para envio do endereço.")
public class EnderecoRequestDTO {

	@NotBlank
	@Schema(description = "Nome da rua onde o endereço está localizado.", example = "Avenida Paulista")
	private String rua;

	@NotBlank
	@Schema(description = "Número do endereço.", example = "1000")
	private String numero;

	@Schema(description = "Informações adicionais sobre o endereço, como complemento ou referência.", example = "Apt 101")
	private String complemento;

	@NotBlank
	@Schema(description = "Bairro onde o endereço está localizado.", example = "Bela Vista")
	private String bairro;

	@NotBlank
	@Schema(description = "Cidade onde o endereço está localizado.", example = "São Paulo")
	private String cidade;

	@NotBlank
	@Schema(description = "Estado onde o endereço está localizado.", example = "SP")
	private String estado;

	@NotBlank
	@Pattern(regexp = "\\d{5}-\\d{3}")
	@Schema(description = "Código de Endereçamento Postal (CEP) do endereço. Deve estar no formato 00000-000.", example = "01311-100")
	private String cep;

	@NotBlank
	@Schema(description = "País onde o endereço está localizado.", example = "Brasil")
	private String pais;

	@Schema(description = "Instruções adicionais para a entrega, como 'Deixar na portaria'.", example = "Deixar na portaria")
	private String instrucaoEntrega;

	@NotNull
	@Schema(description = "Indica se este endereço é o endereço padrão do usuário.", example = "true")
	private Boolean enderecoPadrao;

	@NotNull
	@Schema(description = "Tipo de logradouro, como 'RUA', 'AVENIDA', etc.", example = "RUA")
	private Logradouro logradouro;
}
