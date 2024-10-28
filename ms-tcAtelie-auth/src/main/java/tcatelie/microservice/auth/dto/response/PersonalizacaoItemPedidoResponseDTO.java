package tcatelie.microservice.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tcatelie.microservice.auth.dto.response.OpcaoPersonalizacaoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalizacaoItemPedidoResponseDTO {
    private Integer id;
    private String descricaoPersonalizacao;
    private Double valorPersonalizacao;
    private PersonalizacaoResponseDTO personalizacao;
    private OpcaoPersonalizacaoResponseDTO opcaoPersonalizacao;
}
