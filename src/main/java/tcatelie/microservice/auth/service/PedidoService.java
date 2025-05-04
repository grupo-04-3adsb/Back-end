package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.dto.response.PedidoCardInfoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.EnderecoMapper;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.ItemPedidoRepository;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.repository.PersonalizacaoItemPedidoRepository;
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
  private final ItemPedidoRepository itemPedidoRepository;
  private final PersonalizacaoItemPedidoRepository personalizacaoItemPedidoRepository;
  private final CustoOutrosService custoOutrosService;
  private final ItemPedidoService itemPedidoService;
  private final EnderecoMapper enderecoMapper;
  private final UsuarioMapper usuarioMapper;
  private final ProdutoService produtoService;

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
    } else {
      Pedido novoPedido = new Pedido();
      novoPedido.setStatus(StatusPedido.CARRINHO);
      novoPedido.setUsuario(usuario);
      novoPedido.setResponsaveis(new ArrayList<>());

      return transformarPedido(repository.save(novoPedido));
    }

  }

  public Page<Pedido> getPedidos(PedidoFiltroDTO filtro, PageRequest pageRequest) {

    return repository.findAll(PedidoSpecification.filterBy(filtro, filtro.getStatusExcluidos() == null ? List.of() : filtro.getStatusExcluidos().stream().map(
            StatusPedido::valueOf
    ).toList()), pageRequest);
  }

  public List<Pedido> buscarPedidosPorStatus(StatusPedido status) {
    return repository.findByStatus(status);
  }

  public void atualizarStatusPedido(Pedido pedido, StatusPedido novoStatus) {
    pedido.setStatus(novoStatus);
    repository.save(pedido);
  }

  public List<PedidoResponseDTO> findAll(PedidoFiltroDTO filtroDTO) {
    return repository.findAll(PedidoSpecification.filterBy(filtroDTO, List.of(StatusPedido.CARRINHO))).stream()
            .map(this::transformarPedido)
            .toList();
  }

  public List<PedidoResponseDTO> findAll(PedidoFiltroDTO filtroDTO, Pageable page) {
    return repository.findAll(PedidoSpecification.filterBy(filtroDTO, List.of(StatusPedido.CARRINHO)), page).stream()
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
        pedido.setValorFrete(pedidoRequestDTO.getValorFrete());
        pedido.setValorTotal(calcularValorTotalPedidoAtualizado(pedidoRequestDTO));
        pedido.setValorDesconto(pedido.getItens().stream().mapToDouble(
                        item -> item.getProduto().getPreco() * (item.getProduto().getDesconto() / 100) * item.getQuantidade()
                ).sum()
        );
        pedido.getItens().stream().forEach(i -> {
          i.setProdutoFeito(false);
          i.setDesconto(i.getProduto().getDesconto());
          i.setValorDesconto(i.getProduto().getPreco() * (i.getProduto().getDesconto() / 100));
          i.setValorTotal(
                  itemPedidoService.calcularValorTotalCarrinho(i)
          );
          i.setValor(
                  (i.getProduto().getPreco() - i.getProduto().getPreco() * (i.getProduto().getDesconto() / 100) +
                          i.getPersonalizacoes().stream().mapToDouble(p -> p.getOpcaoPersonalizacao().getAcrescimoOpcao()).sum()
                  )
          );
          i.getPersonalizacoes().stream().forEach(p -> {
            p.setValorPersonalizacao(p.getOpcaoPersonalizacao().getAcrescimoOpcao());
          });
        });
        if (pedidoRequestDTO.getEnderecoEntrega() == null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço é obrigatório para o pedido");
        }
        Endereco endereco = new Endereco();
        EnderecoRequestDTO requestEndereco = pedidoRequestDTO.getEnderecoEntrega();
        endereco.setCep(requestEndereco.getCep());
        endereco.setCidade(requestEndereco.getCidade());
        endereco.setBairro(requestEndereco.getBairro());
        endereco.setComplemento(requestEndereco.getComplemento());
        endereco.setInstrucaoEntrega(requestEndereco.getInstrucaoEntrega());
        endereco.setRua(requestEndereco.getRua());
        endereco.setNumero(requestEndereco.getNumero());
        endereco.setLogradouro(requestEndereco.getLogradouro());
        endereco.setEstado(requestEndereco.getEstado());
        endereco.setPais(requestEndereco.getPais());
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataEntrega(LocalDateTime.now().plusDays(pedidoRequestDTO.getTempoEntrega()));
        break;
      case PENDENTE_PAGAMENTO:
        pedido.setDataPedido(LocalDateTime.now());
        break;
      case PENDENTE:
        pedido.getItens().stream().forEach(i -> i.setProdutoFeito(false));
        break;
      case EM_PREPARO:
        pedido.getItens().stream().forEach(i -> {
          i.setCustoProducao((i.getProduto().getMateriaisProduto().stream().mapToDouble(
                  materialProduto -> materialProduto.getMaterial().getPrecoUnitario() * materialProduto.getQtdMaterialNecessario()
          ).sum() +
                  custosOutros.stream().mapToDouble(outroCusto -> outroCusto.getValor()).sum()
          ) * i.getQuantidade());
        });
        break;
      case EM_ROTA:
        pedido.getItens().stream().forEach(i -> i.setProdutoFeito(true));
        pedido.setDataConclusao(LocalDateTime.now());
        break;
      default:
        break;
    }

    pedido.setCodigoRastreio(pedidoRequestDTO.getCodigoRastreio());

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

    if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
      response.setItens(new ArrayList<>());
    } else {
      response.setItens(pedido.getItens().stream().map(itemPedidoService::transformarItemPedidoResponseDTO).toList());
    }

    response.setEnderecoEntrega(enderecoMapper.toEnderecoResponseDTO(pedido.getEnderecoEntrega()));

    if (pedido.getUsuario() == null) {
      UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
      usuarioResponseDTO.setNome(pedido.getNomeUsuario());
      response.setCliente(usuarioResponseDTO);
    } else {
      response.setCliente(usuarioMapper.toUsuarioResponseDTO(pedido.getUsuario()));
    }
    response.setTotalCustoProducao(
            response.getItens()
                    .stream()
                    .mapToDouble(item -> item.getCustoProducao() != null ? item.getCustoProducao() : 0.0)
                    .sum()
    );

    response.setDataPedido(Optional.ofNullable(pedido.getDataPedido()).orElse(LocalDateTime.now()).toString());

    response.setDataPedido(DateFormat.formatToCustomPattern(pedido.getDataPedido()));
    response.setDataEntrega(DateFormat.format(pedido.getDataEntrega(), "dd/MM/yyyy"));
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

  public PedidoCardInfoResponseDTO transformarPedidoCardInfo(Pedido pedido){
    PedidoCardInfoResponseDTO response = new PedidoCardInfoResponseDTO();

    response.setDataPedido(DateFormat.formatToCustomPattern(pedido.getDataPedido()));
    response.setDataEntrega(DateFormat.format(pedido.getDataEntrega(), "dd/MM/yyyy"));
    response.setId(pedido.getId());
    response.setValorTotal(pedido.getValorTotal());
    response.setEmailCliente(pedido.getUsuario().getEmail());
    response.setNomeUsuario(pedido.getNomeUsuario());
    response.setStatus(pedido.getStatus().name());
    response.setResponsaveis(pedido.getResponsaveis().stream().map(responsavel -> usuarioMapper.toResponsavelResponseDTO(responsavel.getResponsavel())).toList());
    response.setQtdItens(pedido.getItens().stream().mapToInt(ItemPedido::getQuantidade).sum());
    response.setCategorias(pedido.getItens().stream().map(ItemPedido::getProduto).map(Produto::getCategoria).map(Categoria::getNomeCategoria).toList());
    response.setSubcategorias(pedido.getItens().stream().map(ItemPedido::getProduto).map(Produto::getSubcategoria).map(Subcategoria::getNomeSubcategoria).toList());

    return response;
  }

  private Double calcularValorTotalPedidoAtualizado(PedidoRequestDTO pedido) {
    return pedido.getItens().stream().mapToDouble(item -> {
      ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(item.getFkProduto());
      double precoProduto = produto.getPreco();
      double descontoProduto = produto.getDesconto() / 100;
      double precoComDesconto = precoProduto - (precoProduto * descontoProduto);

      ItemPedido itemPedido = itemPedidoRepository.findById(item.getId()).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado")
      );

      double totalPersonalizacoes = itemPedido.getPersonalizacoes().stream().mapToDouble(
              personalizacao -> personalizacao.getOpcaoPersonalizacao().getAcrescimoOpcao()
      ).sum();

      return (precoComDesconto + totalPersonalizacoes) * item.getQuantidade();
    }).sum() + pedido.getValorFrete();
  }

  public PedidoResponseDTO listarUltimoPedido(Integer idUsuario) {
    Pedido pedido = repository.findLastPedidoByUsuarioId(idUsuario).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")
    );

    return transformarPedido(pedido);
  }

  public ResponseEntity atualizarCodigoRastreio(Integer idPedido, String codigoRastreio) {
    Pedido pedido = getPedidoById(idPedido);

    if (pedido.getStatus().equals(StatusPedido.EM_ROTA) || (pedido.getStatus().equals(StatusPedido.EM_PREPARO) &&
            pedido.getItens().stream().allMatch(ItemPedido::getProdutoFeito))) {
      pedido.setCodigoRastreio(codigoRastreio);
      repository.save(pedido);
      return ResponseEntity.noContent().build();
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não está pronto para ser enviado");
    }

  }

  public List<PedidoResponseDTO> listarPedidosFiltrados(PedidoFiltroDTO filtro) {
    return repository.listarPedidos(filtro.getStatusList().stream().map(
                            status -> StatusPedido.valueOf(status)
                    ).toList()
                    , filtro.getIdsResponsaveis(), StringUtils.isNotBlank(filtro.getNomeCliente()) ? filtro.getNomeCliente() : (filtro.getIdPedido() != null ? filtro.getIdPedido().toString() : null),
                    filtro.getDataPedidoInicio()
                    , filtro.getDataPedidoFim()).stream()
            .map(this::transformarPedido)
            .toList();
  }

}
