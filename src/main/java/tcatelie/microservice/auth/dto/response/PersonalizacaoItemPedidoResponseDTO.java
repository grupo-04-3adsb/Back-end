package tcatelie.microservice.auth.dto;

import lombok.*;
import tcatelie.microservice.auth.dto.response.OpcaoPersonalizacaoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoResponseDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalizacaoItemPedidoResponseDTO {
    private Integer id;
    private String descricaoPersonalizacao;
    private Double valorPersonalizacao;
    private PersonalizacaoResponseDTO personalizacao;
    private OpcaoPersonalizacaoResponseDTO opcaoPersonalizacao;
}
