package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.OpcaoPersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.PersonalizacaoMapper;
import tcatelie.microservice.auth.mapper.ProdutoMapper;
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
    private final PersonalizacaoMapper personalizacaoMapper;
    private final OpcaoPersonalizacaoMapper opcaoPersonalizacaoMapper;
    private final ProdutoMapper produtoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPedidoService.class);

    private List<CustoOutros> custosOutros;

    private void verificaCustoOutros() {
        if (custosOutros == null || custosOutros.isEmpty()) {
            custosOutros = custosOutrosRepository.findAll();
        }
    }

    public ItemPedidoResponseDTO adicionarAoCarrinho(Integer idCliente, ItemPedidoRequestDTO itemPedidoRequestDTO) {
        Usuario usuario = userRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Pedido pedido = pedidoRepository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idCliente)
                .orElseGet(() -> criarNovoPedido(usuario));

        itemPedidoRequestDTO.setFkPedido(pedido.getId());

        ItemPedido itemPedido = transformarItemPedido(itemPedidoRequestDTO);
        List<PersonalizacaoItemPedido> personalizacoes = transformarPersonalizacaoItemPedido(itemPedidoRequestDTO, itemPedido);
        if (personalizacoes != null) {
            itemPedido.setPersonalizacoes(personalizacoes);
        }

        if (pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }

        validarUnicidadeItemPedido(itemPedido, pedido.getItens());

        itemPedido.setPedido(pedido);

        pedido.getItens().add(itemPedido);

        repository.save(itemPedido);
        pedidoRepository.save(pedido);

        return transformarItemPedidoResponseDTO(itemPedido);
    }

    private Pedido criarNovoPedido(Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.CARRINHO);
        pedido.setUsuario(usuario);

        Endereco enderecoEntrega = usuario.getEnderecos().stream()
                .filter(Endereco::isEnderecoPadrao)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço de entrega não encontrado"));
        pedido.setEnderecoEntrega(enderecoEntrega);

        return pedidoRepository.save(pedido);
    }

    private void validarUnicidadeItemPedido(ItemPedido novoItem, List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            if (item.getProduto() != null && item.getProduto().getId().equals(novoItem.getProduto().getId())) {
                List<PersonalizacaoItemPedido> personalizacoesExistentes = item.getPersonalizacoes();
                List<PersonalizacaoItemPedido> personalizacoesNovas = novoItem.getPersonalizacoes();

                if (personalizacoesExistentes != null && personalizacoesNovas != null) {
                    boolean listasIguais = personalizacoesExistentes.size() == personalizacoesNovas.size() &&
                            personalizacoesExistentes.stream()
                                    .allMatch(pe -> personalizacoesNovas.stream()
                                            .anyMatch(pn -> personalizacoesIguais(pe, pn))) &&
                            personalizacoesNovas.stream()
                                    .allMatch(pn -> personalizacoesExistentes.stream()
                                            .anyMatch(pe -> personalizacoesIguais(pe, pn)));

                    if (listasIguais) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "Item já adicionado ao carrinho com todas as personalizações iguais");
                    }
                }
            }
        }
    }

    private boolean personalizacoesIguais(PersonalizacaoItemPedido p1, PersonalizacaoItemPedido p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        if (p1.getPersonalizacao() == null || p2.getPersonalizacao() == null) {
            return false;
        }
        if (!p1.getPersonalizacao().getIdPersonalizacao()
                .equals(p2.getPersonalizacao().getIdPersonalizacao())) {
            return false;
        }
        if (p1.getOpcaoPersonalizacao() == null || p2.getOpcaoPersonalizacao() == null) {
            return false;
        }
        if (!p1.getOpcaoPersonalizacao().getIdOpcaoPersonalizacao()
                .equals(p2.getOpcaoPersonalizacao().getIdOpcaoPersonalizacao())) {
            return false;
        }
        return (p1.getDescricaoPersonalizacao() != null && p1.getDescricaoPersonalizacao()
                .equalsIgnoreCase(p2.getDescricaoPersonalizacao()));
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
                        .descricaoPersonalizacao(personalizacaoItemPedidoRequestDTO.getDescricaoPersonalizacao())
                        .build();
                personalizacaoItemPedidos.add(personalizacaoItemPedido);
            });
        }
        return personalizacaoItemPedidos;
    }

    public void finalizarItemPedido(Integer idItemPedido) {
        verificaCustoOutros();

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

    public void concluirItemPedido(Integer idItemPedido) {
        verificaCustoOutros();

        ItemPedido itemPedido = repository.findById(idItemPedido).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado"));
        itemPedido.setCustoProducao(
                itemPedido.getProduto().getMateriaisProduto().stream().mapToDouble(materialProduto -> materialProduto.getMaterial().getPrecoUnitario() * materialProduto.getQtdMaterialNecessario()).sum()
                        * itemPedido.getQuantidade() + itemPedido.getQuantidade() * custosOutros.stream().mapToDouble(CustoOutros::getValor).sum()
        );
        itemPedido.setProdutoFeito(true);
        repository.save(itemPedido);
    }

    public ItemPedidoResponseDTO transformarItemPedidoResponseDTO(ItemPedido item) {
        Pedido pedido = item.getPedido();
        StatusPedido status = pedido.getStatus();

        ItemPedidoResponseDTO.ItemPedidoResponseDTOBuilder builder = ItemPedidoResponseDTO.builder()
                .id(item.getId())
                .quantidade(item.getQuantidade())
                .personalizacoes(mapearPersonalizacoes(item))
                .produto(produtoMapper.toResponseDTO(item.getProduto()))
                .feito(status != StatusPedido.CARRINHO && status != StatusPedido.PENDENTE_PAGAMENTO);

        switch (status) {
            case CARRINHO -> preencherDadosCarrinho(item, builder);
            case PENDENTE_PAGAMENTO, PENDENTE, EM_PREPARO -> preencherDadosComuns(item, builder);
            default -> preencherDadosConcluido(item, builder);
        }

        return builder.build();
    }

    private void preencherDadosCarrinho(ItemPedido item, ItemPedidoResponseDTO.ItemPedidoResponseDTOBuilder builder) {
        builder.valor(item.getProduto().getPreco())
                .valorTotal(calcularValorTotalCarrinho(item))
                .valorFrete(0.0)
                .valorDesconto(calcularValorDesconto(item))
                .desconto(item.getProduto().getDesconto())
                .custoProducao(calcularCustoProducao(item));
    }

    private void preencherDadosComuns(ItemPedido item, ItemPedidoResponseDTO.ItemPedidoResponseDTOBuilder builder) {
        builder.valor(item.getValor())
                .valorTotal(item.getValorTotal())
                .valorFrete(item.getValorFrete())
                .valorDesconto(item.getValorDesconto())
                .desconto(item.getDesconto())
                .custoProducao(calcularCustoProducao(item));
    }

    private void preencherDadosConcluido(ItemPedido item, ItemPedidoResponseDTO.ItemPedidoResponseDTOBuilder builder) {
        builder.valor(item.getValor())
                .valorTotal(item.getValorTotal())
                .valorFrete(item.getValorFrete())
                .valorDesconto(item.getValorDesconto())
                .desconto(item.getDesconto())
                .custoProducao(item.getCustoProducao());
    }

    private double calcularValorTotalCarrinho(ItemPedido item) {
        double desconto = calcularValorDesconto(item);
        double acrescimos = item.getPersonalizacoes()
                .stream()
                .mapToDouble(p -> p.getOpcaoPersonalizacao().getAcrescimoOpcao())
                .sum();
        return (item.getProduto().getPreco() * item.getQuantidade() - desconto) + acrescimos;
    }

    private double calcularValorDesconto(ItemPedido item) {
        return item.getProduto().getPreco() * (item.getProduto().getDesconto() / 100) * item.getQuantidade();
    }

    private double calcularCustoProducao(ItemPedido item) {
        verificaCustoOutros();

        double custoMateriais = item.getProduto().getMateriaisProduto()
                .stream()
                .mapToDouble(m -> m.getMaterial().getPrecoUnitario() * m.getQtdMaterialNecessario())
                .sum();
        double custoOutros = item.getQuantidade() * custosOutros.stream().mapToDouble(CustoOutros::getValor).sum();
        return custoMateriais + custoOutros;
    }

    private List<PersonalizacaoItemPedidoResponseDTO> mapearPersonalizacoes(ItemPedido item) {
        if(item.getPersonalizacoes() == null) {
            return new ArrayList<>();
        }

        return item.getPersonalizacoes().stream()
                .map(p -> PersonalizacaoItemPedidoResponseDTO.builder()
                        .id(p.getId())
                        .personalizacao(personalizacaoMapper.toPersonalizacaoResponseDTO(p.getPersonalizacao()))
                        .valorPersonalizacao(p.getOpcaoPersonalizacao().getAcrescimoOpcao())
                        .opcaoPersonalizacao(opcaoPersonalizacaoMapper.toOpcaoPersonalizacaoResponseDTO(p.getOpcaoPersonalizacao()))
                        .descricaoPersonalizacao(p.getDescricaoPersonalizacao())
                        .build())
                .toList();
    }

    public void removerItemPedido(Integer idItemPedido) {
        ItemPedido itemPedido = repository.findById(idItemPedido).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado"));
        repository.deleteById(itemPedido.getId());
    }

    public void alterarQuantidade(Integer idItemPedido, Integer quantidade) {
        ItemPedido itemPedido = repository.findById(idItemPedido).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pedido não encontrado"));
        itemPedido.setQuantidade(quantidade);
        repository.save(itemPedido);
    }
}
