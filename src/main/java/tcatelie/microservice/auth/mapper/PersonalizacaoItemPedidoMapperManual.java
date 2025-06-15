package tcatelie.microservice.auth.mapper;

import tcatelie.microservice.auth.dto.response.OpcaoPersonalizacaoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoResponseDTO;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

public class PersonalizacaoItemPedidoMapperManual {

  public static PersonalizacaoItemPedidoResponseDTO personalizacaoItemPedidoToResponseDTO(PersonalizacaoItemPedido personalizacaoItemPedido) {
    return PersonalizacaoItemPedidoResponseDTO.builder()
            .id(personalizacaoItemPedido.getId())
            .descricaoPersonalizacao(personalizacaoItemPedido.getDescricaoPersonalizacao())
            .valorPersonalizacao(personalizacaoItemPedido.getValorPersonalizacao())
            .personalizacao(
                    PersonalizacaoResponseDTO.builder()
                            .idPersonalizacao(personalizacaoItemPedido.getPersonalizacao().getIdPersonalizacao())
                            .nomePersonalizacao(personalizacaoItemPedido.getPersonalizacao().getNomePersonalizacao())
                            .build()
            )
            .opcaoPersonalizacao(
                    OpcaoPersonalizacaoResponseDTO.builder()
                            .idOpcao(personalizacaoItemPedido.getOpcaoPersonalizacao().getIdOpcaoPersonalizacao())
                            .descricaoOpcao(personalizacaoItemPedido.getOpcaoPersonalizacao().getDescricao())
                            .acrescimo(personalizacaoItemPedido.getOpcaoPersonalizacao().getAcrescimoOpcao())
                            .urlImagemOpcao(personalizacaoItemPedido.getOpcaoPersonalizacao().getUrlImagemOpcao())
                            .nomeOpcao(personalizacaoItemPedido.getOpcaoPersonalizacao().getNomeOpcao())
                            .build())
            .build();
  }

}
