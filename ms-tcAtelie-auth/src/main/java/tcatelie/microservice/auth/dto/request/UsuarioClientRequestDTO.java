package tcatelie.microservice.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioClientRequestDTO {
    private Integer idUsuario;
    private String nome;
    private String nomeCompleto;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String genero;
    private String imgUrl;
    private String profissao;
    private String numeroTelefone;
    private String cpf;

    private List<String> cargos;
    private List<EnderecoRequestDTO> listaEnderecos;
}
