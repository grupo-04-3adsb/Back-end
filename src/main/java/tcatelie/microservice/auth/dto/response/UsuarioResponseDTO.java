package tcatelie.microservice.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Response DTO do usuário.")
public class UsuarioResponseDTO {

    @Schema(description = "Nome completo do usuário.", example = "Cláudio Araújo")
    private String nome;

    @Schema(description = "Email do usuário.", example = "claudio@gmail.com")
    private String email;

    @Schema(description = "Gênero do usuário.", example = "MASCULINO")
    private String genero;

    @Schema(description = "Número de telefone do usuário.", example = "(11) 98765-4321")
    private String numeroTelefone;

    @Schema(description = "CPF do usuário.", example = "123.456.789-09")
    private String cpf;

    @Schema(description = "Senha do usuário. Este campo não deve ser exposto por questões de segurança.", example = "********")
    private String senha;

    @Schema(description = "URL da imagem do perfil do usuário.", example = "http://img.png")
    private String imgUrl;

    @Schema(description = "ID da imagem do perfil do usuário no Google Drive.", example = "123456")
    private String idImgDrive;

    @Schema(description = "Status do usuário (ex: ATIVO, INATIVO).", example = "ATIVO")
    private String status;

    @Schema(description = "Lista de endereços associados ao usuário.", example = "[{\"id\": 1, \"rua\": \"Avenida Paulista\", \"numero\": \"1000\"}]")
    private List<EnderecoResponseDTO> enderecos;

    @Schema(description = "Cargo do usuário.", example = "Desenvolvedor")
    private String cargo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(description = "Data de nascimento do usuário.", example = "01/01/2000")
    private LocalDate dataNascimento;

    @Schema(description = "id google do usuário.", example = "123456")
    private Integer idGoogle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data de criação do registro do usuário.", example = "01/01/2023 10:00:00")
    private LocalDateTime dataCriacao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data da última atualização do registro do usuário.", example = "05/01/2023 10:00:00")
    private LocalDateTime dataAtualizacao;
}
