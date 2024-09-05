package tcatelie.microservice.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnderecoRequestDTO {

    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
    private String nomeContato;
    private String telefoneContato;
    private String emailContato;
    private String instrucoesEntrega;
    private boolean enderecoPadrao;
    private String tipo;
}
