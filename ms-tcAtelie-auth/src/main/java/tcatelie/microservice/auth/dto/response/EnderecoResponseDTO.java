package tcatelie.microservice.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Logradouro;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response DTO para envio do endereço")
public class EnderecoResponseDTO {

    @Schema(description = "ID do endereço.", example = "1")
    private Integer id;

    @Schema(description = "Nome da rua.", example = "Avenida Paulista")
    private String rua;

    @Schema(description = "Número do endereço.", example = "1000")
    private String numero;

    @Schema(description = "Informações adicionais sobre o endereço.", example = "Apt 101")
    private String complemento;

    @Schema(description = "Bairro do endereço.", example = "Bela Vista")
    private String bairro;

    @Schema(description = "Cidade do endereço.", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado do endereço.", example = "SP")
    private String estado;

    @Schema(description = "CEP do endereço.", example = "01311-100")
    private String cep;

    @Schema(description = "País do endereço.", example = "Brasil")
    private String pais;

    @Schema(description = "Instruções de entrega para o endereço.", example = "Deixar na portaria")
    private String instrucaoEntrega;

    @Schema(description = "Indica se este é o endereço padrão do usuário.", example = "true")
    private Boolean enderecoPadrao;

    @Schema(description = "Tipo de logradouro, como 'RUA' ou 'AVENIDA'.", example = "RUA")
    private Logradouro logradouro;

    @Schema(description = "Data e hora de cadastro do endereço.", example = "2023-01-01T10:00:00")
    private LocalDateTime dthrCadastro;

    @Schema(description = "Data e hora da última atualização do endereço.", example = "2023-01-05T10:00:00")
    private LocalDateTime dthrAtualizacao;
}
