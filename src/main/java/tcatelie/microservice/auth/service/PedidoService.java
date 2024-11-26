package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.EnderecoMapper;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.model.Usuario;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.repository.UserRepository;
import tcatelie.microservice.auth.specification.PedidoSpecification;
import tcatelie.microservice.auth.util.DateFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final UserRepository userRepository;
    private final PedidoMapper mapper;
    private final CustoOutrosService custoOutrosService;
    private final ItemPedidoService itemPedidoService;
    private final EnderecoMapper enderecoMapper;
    private final UsuarioMapper usuarioMapper;

    private List<CustoOutrosResponseDTO> custosOutros = new ArrayList<>();

    public Pedido getPedidoById(Integer idPedido) {
        return repository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    public PedidoResponseDTO carregarCarrinhoUsuario(Integer idUsuario) {
        Usuario usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Optional<Pedido> pedido = repository.findByStatusAndUsuario_IdUsuario(StatusPedido.CARRINHO, idUsuario);

        if (pedido.isPresent()) {
            return transformarPedido(pedido.get());
        } else{
            Pedido novoPedido = new Pedido();
            novoPedido.setStatus(StatusPedido.CARRINHO);
            novoPedido.setUsuario(usuario);

            return transformarPedido(repository.save(novoPedido));
        }

    }

    public Page<PedidoResponseDTO> getPedidos(PageRequest pageRequest) {

        Page<Pedido> pedidos = repository.findByStatusIn(List.of(
                        StatusPedido.PENDENTE_PAGAMENTO,
                        StatusPedido.PENDENTE,
                        StatusPedido.CONCLUIDO,
                        StatusPedido.CONCLUIDO,
                        StatusPedido.EM_PREPARO,
                        StatusPedido.EM_ROTA),
                pageRequest);


        return pedidos.map(this::transformarPedido);
    }

    public List<Pedido> buscarPedidosPorStatus(StatusPedido status) {
        return repository.findByStatus(status);
    }

    public void atualizarStatusPedido(Pedido pedido, StatusPedido novoStatus) {
        pedido.setStatus(novoStatus);
        repository.save(pedido);
    }

    public List<PedidoResponseDTO> findAll(PedidoFiltroDTO filtroDTO) {
        return repository.findAll(PedidoSpecification.filterBy(filtroDTO)).stream()
                .map(this::transformarPedido)
                .toList();
    }

    public ResponseEntity updatePedido(Integer idPedido, PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = getPedidoById(idPedido);

        validaStatusPedido(pedidoRequestDTO, pedido);

        StatusPedido statusAnterior = pedido.getStatus();

        pedido.setStatus(StatusPedido.valueOf(pedidoRequestDTO.getStatusPedido()));

        switch (pedido.getStatus()) {
            case CARRINHO:
                pedido.setDataPedido(null);
                pedido.setValorFrete(pedidoRequestDTO.getValorFrete());
                pedido.setValorDesconto(pedido.getItens().stream().mapToDouble(
                                item -> item.getProduto().getPreco() * (item.getProduto().getDesconto() / 100) * item.getQuantidade()
                        ).sum()
                );
                pedido.setValorTotal(
                        pedido.getItens().stream().mapToDouble(
                                item -> (item.getProduto().getPreco())
                                        + item.getPersonalizacoes().stream().mapToDouble(
                                        personalizacao -> personalizacao.getOpcaoPersonalizacao().getAcrescimoOpcao()
                                ).sum()
                                        * item.getQuantidade()
                        ).sum() + pedido.getValorFrete() - pedido.getValorDesconto()
                );
                pedido.getItens().stream().forEach(i -> {
                    i.setProdutoFeito(false);
                    i.setDesconto(i.getProduto().getDesconto());
                    i.setValorDesconto(i.getProduto().getPreco() * (i.getProduto().getDesconto() / 100));
                    i.setValorTotal(
                            (i.getProduto().getPreco() + i.getPersonalizacoes().stream().mapToDouble(
                                    personalizacao -> personalizacao.getOpcaoPersonalizacao().getAcrescimoOpcao()
                            ).sum()) * i.getQuantidade() - i.getValorDesconto()
                    );
                    i.getPersonalizacoes().stream().forEach(p -> {
                        p.setValorPersonalizacao(p.getOpcaoPersonalizacao().getAcrescimoOpcao());
                    });
                });
                break;
            case PENDENTE_PAGAMENTO:
                pedido.setDataPedido(LocalDateTime.now());
                break;
            case PENDENTE:
                break;
            case EM_PREPARO:
                pedido.getItens().stream().forEach(i -> i.setProdutoFeito(true));
                pedido.getItens().stream().forEach(i -> {
                    i.setCustoProducao((i.getProduto().getMateriaisProduto().stream().mapToDouble(
                            materialProduto -> materialProduto.getMaterial().getPrecoUnitario() * materialProduto.getQtdMaterialNecessario()
                    ).sum() +
                            custosOutros.stream().mapToDouble(outroCusto -> outroCusto.getValor()).sum()
                    ) * i.getQuantidade());
                });
                break;
            case EM_ROTA:
                pedido.setDataConclusao(LocalDateTime.now());
                break;
            default:
                break;
        }

        repository.save(pedido);
        return ResponseEntity.noContent().build();
    }

    public void validaStatusPedido(PedidoRequestDTO pedidoRequestDTO, Pedido pedido) {
        StatusPedido pedidoAtual = pedido.getStatus();
        StatusPedido novoStatus = StatusPedido.valueOf(pedidoRequestDTO.getStatusPedido().toUpperCase());

        if (pedidoAtual.equals(novoStatus)) {
            return;
        }

        if (pedidoAtual.equals(StatusPedido.CONCLUIDO) && !pedidoAtual.equals(novoStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido já foi concluído e não pode ser alterado!");
        }

        if (novoStatus.equals(StatusPedido.CANCELADO)) {
            return;
        }

        if (pedidoAtual.equals(StatusPedido.PENDENTE_PAGAMENTO)) {
            if (novoStatus.equals(StatusPedido.CARRINHO) || novoStatus.equals(StatusPedido.PENDENTE)) {
                return;
            }
        }

        switch (pedidoAtual) {
            case CARRINHO:
                if (novoStatus != StatusPedido.PENDENTE) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido precisa avançar para Pendente a partir do Carrinho.");
                }
                break;
            case PENDENTE:
                if (novoStatus != StatusPedido.EM_PREPARO) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido precisa avançar para Em preparo a partir de Pendente.");
                }
                break;
            case EM_PREPARO:
                if (novoStatus != StatusPedido.EM_ROTA) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido precisa avançar para Em rota a partir de Em preparo.");
                }
                break;
            case EM_ROTA:
                if (novoStatus != StatusPedido.CONCLUIDO) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido precisa avançar para Concluído a partir de Em rota.");
                }
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transição de status inválida.");
        }
    }

    public PedidoResponseDTO transformarPedido(Pedido pedido) {
        PedidoResponseDTO response = mapper.pedidoToPedidoResponseDTO(pedido);

        if (custosOutros.isEmpty()) {
            custosOutros = custoOutrosService.findAll();
        }

        response.setItens(pedido.getItens().stream().map(itemPedidoService::transformarItemPedidoResponseDTO).toList());
        response.setEnderecoEntrega(enderecoMapper.toEnderecoResponseDTO(pedido.getEnderecoEntrega()));

        if (pedido.getUsuario() == null) {
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
            usuarioResponseDTO.setNome(pedido.getNomeUsuario());
            response.setCliente(usuarioResponseDTO);
        } else {
            response.setCliente(usuarioMapper.toUsuarioResponseDTO(pedido.getUsuario()));
        }
        response.setTotalCustoProducao(
                response.getItens().stream().mapToDouble(ItemPedidoResponseDTO::getCustoProducao).sum()
        );
        response.setDataPedido(pedido.getDataPedido().toString());

        response.setDataPedido(DateFormat.formatToCustomPattern(pedido.getDataPedido()));
        response.setDataEntrega(DateFormat.formatToCustomPattern(pedido.getDataConclusao()));
        response.setDataCancelamento(DateFormat.formatToCustomPattern(pedido.getDataCancelamento()));
        response.setDataPagamento(DateFormat.formatToCustomPattern(pedido.getDataPagamento()));

        response.setId(pedido.getId());
        response.setFormaPgto(pedido.getFormaPgto());
        response.setObservacao(pedido.getObservacao());
        response.setValorFrete(pedido.getValorFrete());
        response.setParcelas(1);
        response.setValorTotal(pedido.getValorTotal());
        response.setStatus(pedido.getStatus().name());
        response.setResponsaveis(pedido.getResponsaveis().stream().map(responsavel -> usuarioMapper.toResponsavelResponseDTO(responsavel.getResponsavel())).toList());

        return response;
    }

}
