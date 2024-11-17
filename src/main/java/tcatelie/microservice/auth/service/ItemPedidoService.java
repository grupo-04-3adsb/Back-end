package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;
    private final PersonalizacaoRepository personalizacaoRepository;
    private final OpcaoPersonalizacaoRepository opcaoPersonalizacaoRepository;
    private final PersonalizacaoItemPedidoRepository personalizacaoItemPedidoRepository;
    private final CustosOutrosRepository custosOutrosRepository;

    private List<CustoOutros> custosOutros = custosOutrosRepository.findAll();

    public void adicionarAoCarrinho(Integer idCliente, ItemPedidoRequestDTO itemPedidoRequestDTO) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente);
        Usuario usuario = userRepository.findById(idCliente).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Pedido pedido;

        if (pedidoOpt.isEmpty()) {
            pedido = new Pedido();
            pedido.setStatus(StatusPedido.CARRINHO);
            pedido.setUsuario(usuario);
            pedido.setEnderecoEntrega(usuario.getEnderecos().stream().filter(endereco -> endereco.isEnderecoPadrao()).findFirst().orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço de entrega não encontrado")));
            pedido.setItens(new ArrayList<>());
            pedido = pedidoRepository.save(pedido);
        } else {
            pedido = pedidoOpt.get();
            if (pedido.getItens() == null) {
                pedido.setItens(new ArrayList<>());
            }
        }

        itemPedidoRequestDTO.setFkPedido(pedido.getId());
        ItemPedido itemPedido = transformarItemPedido(itemPedidoRequestDTO);
        itemPedido = repository.save(itemPedido);

        pedido.getItens().add(itemPedido);
        pedidoRepository.save(pedido);

        List<PersonalizacaoItemPedido> personalizacaoItemPedidos = transformarPersonalizacaoItemPedido(itemPedidoRequestDTO, itemPedido);
        personalizacaoItemPedidos.forEach(personalizacaoItemPedidoRepository::save);
    }

    private ItemPedido transformarItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO) {

        return ItemPedido.builder()
                .quantidade(itemPedidoRequestDTO.getQuantidade())
                .valor(itemPedidoRequestDTO.getValor())
                .valorTotal(itemPedidoRequestDTO.getValorTotal())
                .desconto(itemPedidoRequestDTO.getDesconto())
                .valorDesconto(itemPedidoRequestDTO.getValorDesconto())
                .valorFrete(itemPedidoRequestDTO.getValorFrete())
                .custoProducao(itemPedidoRequestDTO.getCustoProducao())
                .produtoFeito(itemPedidoRequestDTO.getProdutoFeito())
                .produto(produtoRepository.findById(itemPedidoRequestDTO.getFkProduto()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")))
                .pedido(pedidoRepository.findById(itemPedidoRequestDTO.getFkPedido()).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")))
                .build();
    }


    private List<PersonalizacaoItemPedido> transformarPersonalizacaoItemPedido(ItemPedidoRequestDTO itemPedidoRequestDTO, ItemPedido itemPedido) {
        List<PersonalizacaoItemPedido> personalizacaoItemPedidos = new ArrayList<>();
        if (itemPedidoRequestDTO.getPersonalizacoes() != null) {
            itemPedidoRequestDTO.getPersonalizacoes().forEach(personalizacaoItemPedidoRequestDTO -> {
                PersonalizacaoItemPedido personalizacaoItemPedido = PersonalizacaoItemPedido.builder()
                        .personalizacao(personalizacaoRepository.findById(personalizacaoItemPedidoRequestDTO.getFkPersonalizacao()).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização não encontrada")))
                        .opcaoPersonalizacao(opcaoPersonalizacaoRepository.findById(personalizacaoItemPedidoRequestDTO.getFkOpcaoPersonalizacao()).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção de personalização não encontrada")))
                        .itemPedido(itemPedido)
                        .build();
                personalizacaoItemPedidos.add(personalizacaoItemPedido);
            });
        }
        return personalizacaoItemPedidos;
    }

    public void finalizarItemPedido(Integer idItemPedido) {

        ItemPedido itemPedido = repository.findById(idItemPedido).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado"));
        itemPedido.setProdutoFeito(true);

        itemPedido.setCustoProducao(
                (itemPedido.getProduto().getMateriaisProduto().stream().mapToDouble(materialProduto -> materialProduto.getMaterial().getPrecoUnitario() * materialProduto.getQtdMaterialNecessario()).sum()
                        * itemPedido.getQuantidade()) + (itemPedido.getQuantidade() * custosOutros.stream().mapToDouble(CustoOutros::getValor).sum())
        );

        repository.save(itemPedido);
    }

    public void atualizarItemPedidoPagamentoAprovado(ItemPedido itemPedido) {
        itemPedido.setDesconto(
                itemPedido.getProduto().getDesconto()
        );
        itemPedido.setValorDesconto((itemPedido.getProduto().getPreco()
                * itemPedido.getDesconto() / 100) * itemPedido.getQuantidade());

        itemPedido.getPersonalizacoes().stream().forEach(personalizacaoItemPedido -> {
            personalizacaoItemPedido.setValorPersonalizacao(personalizacaoItemPedido.getOpcaoPersonalizacao().getAcrescimoOpcao());
        });

        itemPedido.setValor((itemPedido.getProduto().getPreco()
                + itemPedido.getPersonalizacoes().stream().mapToDouble(personalizacaoItemPedido -> personalizacaoItemPedido.getValorPersonalizacao()).sum()
                + itemPedido.getValorFrete()
        ) * itemPedido.getQuantidade());

        itemPedido.setValorTotal(itemPedido.getValor() - itemPedido.getValorDesconto());
    }
}
