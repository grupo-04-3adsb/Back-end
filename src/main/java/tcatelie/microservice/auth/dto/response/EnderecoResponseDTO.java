package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.enums.Logradouro;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private Integer id;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
    private String instrucaoEntrega;
    private Boolean enderecoPadrao;
    private Logradouro logradouro;
    private LocalDateTime dthrCadastro;
    private LocalDateTime dthrAtualizacao;

}
