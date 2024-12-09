package tcatelie.microservice.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsavelResponseDTO {

    private Integer idResponsavel;
    private String nome;
    private String email;
    private String telefone;
    private String urlImg;
    private String cpf;
    private String genero;
    private String status;
    private String role;
    private String dthrCadastro;
    private String dthrAtualizacao;
}
