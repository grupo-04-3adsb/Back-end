package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
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

    private List<CustoOutros> custosOutros;

    private void verificaCustoOutros() {
        if (custosOutros == null || custosOutros.isEmpty()) {
            custosOutros = custosOutrosRepository.findAll();
        }
    }

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

        if(pedido.getItens() == null) {
            pedido.setItens(new ArrayList<>());
        }

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
                        .build())
                .toList();
    }
}
