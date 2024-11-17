package tcatelie.microservice.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.OpcaoPersonalizacaoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoResponseDTO;
import tcatelie.microservice.auth.model.ItemPedido;
import tcatelie.microservice.auth.model.PersonalizacaoItemPedido;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
@Component
public interface ItemPedidoMapper {

    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "produtoFeito", target = "feito")
    @Mapping(target = "personalizacoes", source = "personalizacoes", qualifiedByName = "personalizacaoItemPedido")
    ItemPedidoResponseDTO itemPedidoToItemPedidoResponseDTO(ItemPedido itemPedido);

    @Named("personalizacaoItemPedido")
    default List<PersonalizacaoItemPedidoResponseDTO> personalizacaoItemPedidoToPersonalizacaoItemPedidoResponseDTO(List<PersonalizacaoItemPedido> personalizacoes) {
        if (personalizacoes == null) {
            return List.of();
        }

        return personalizacoes.stream().map(personalizacao -> {
                    if (personalizacao == null) {
                        return null;
                    }

                    PersonalizacaoResponseDTO personalizacaoDTO = null;
                    if (personalizacao.getPersonalizacao() != null) {
                        personalizacaoDTO = new PersonalizacaoResponseDTO();
                        personalizacaoDTO.setIdPersonalizacao(personalizacao.getPersonalizacao().getIdPersonalizacao());
                        personalizacaoDTO.setNomePersonalizacao(personalizacao.getPersonalizacao().getNomePersonalizacao());
                        personalizacaoDTO.setTipoPersonalizacao(personalizacao.getPersonalizacao().getTipoPersonalizacao());
                        personalizacaoDTO.setDthrCriacao(personalizacao.getPersonalizacao().getDthrCadastro());
                        personalizacaoDTO.setDthrAtualizacao(personalizacao.getPersonalizacao().getDthrAtualizacao());

                        if (personalizacao.getPersonalizacao().getOpcoes() != null) {
                            personalizacaoDTO.setOpcoes(personalizacao.getPersonalizacao().getOpcoes().stream().map(opcao -> {
                                OpcaoPersonalizacaoResponseDTO opcaoDTO = new OpcaoPersonalizacaoResponseDTO();
                                opcaoDTO.setIdOpcao(opcao.getIdOpcaoPersonalizacao());
                                opcaoDTO.setNomeOpcao(opcao.getNomeOpcao());
                                opcaoDTO.setAcrescimo(opcao.getAcrescimoOpcao());
                                return opcaoDTO;
                            }).collect(Collectors.toList()));
                        }
                    }

                    OpcaoPersonalizacaoResponseDTO opcaoPersonalizacaoDTO = null;
                    if (personalizacao.getOpcaoPersonalizacao() != null) {
                        opcaoPersonalizacaoDTO = new OpcaoPersonalizacaoResponseDTO();
                        opcaoPersonalizacaoDTO.setIdOpcao(personalizacao.getOpcaoPersonalizacao().getIdOpcaoPersonalizacao());
                        opcaoPersonalizacaoDTO.setNomeOpcao(personalizacao.getOpcaoPersonalizacao().getNomeOpcao());
                        opcaoPersonalizacaoDTO.setAcrescimo(personalizacao.getOpcaoPersonalizacao().getAcrescimoOpcao());
                        opcaoPersonalizacaoDTO.setDthrAtualizacao(personalizacao.getOpcaoPersonalizacao().getDthrAtualizacao());
                        opcaoPersonalizacaoDTO.setDthrCriacao(personalizacao.getOpcaoPersonalizacao().getDthrCadastro());
                    }

                    return PersonalizacaoItemPedidoResponseDTO.builder()
                            .id(personalizacao.getId())
                            .descricaoPersonalizacao(personalizacao.getDescricaoPersonalizacao())
                            .valorPersonalizacao(personalizacao.getValorPersonalizacao())
                            .personalizacao(personalizacaoDTO)
                            .opcaoPersonalizacao(opcaoPersonalizacaoDTO)
                            .build();
                }).filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

}
