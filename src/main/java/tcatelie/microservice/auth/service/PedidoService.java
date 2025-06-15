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
import tcatelie.microservice.auth.dto.response.ItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.response.PersonalizacaoItemPedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.EnderecoRequestDTO;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.dto.request.PersonalizacaoItemPedidoRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.dto.response.PedidoCardInfoResponseDTO;
import tcatelie.microservice.auth.dto.response.ProdutoResponseDTO;
import tcatelie.microservice.auth.dto.response.UsuarioResponseDTO;
import tcatelie.microservice.auth.dto.revison.PedidoRevisaoResponseDTO;
import tcatelie.microservice.auth.enums.OrigemPedido;
import tcatelie.microservice.auth.enums.Status;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.enums.UserRole;
import tcatelie.microservice.auth.mapper.EnderecoMapper;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.mapper.UsuarioMapper;
import tcatelie.microservice.auth.model.*;
import tcatelie.microservice.auth.repository.*;
import tcatelie.microservice.auth.specification.PedidoSpecification;
import tcatelie.microservice.auth.util.DateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
  private final PersonalizacaoRepository personalizacaoRepository;
  private final EnderecoRepository enderecoRepository;

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
      novoPedido.setNomeUsuario(usuario.getNome());
      novoPedido.setTelefoneCliente(usuario.getTelefone());

      return transformarPedido(repository.save(novoPedido));
    }

  }

  public List<Pedido> getPedidos(PedidoFiltroDTO filtro) {

    return repository.findAll(PedidoSpecification.filterBy(filtro, filtro.getStatusExcluidos() == null ? List.of() : filtro.getStatusExcluidos().stream().map(
            StatusPedido::valueOf
    ).toList()));
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
                        item -> item.getProduto().getPreco() * (item.getProduto().getDesconto() == null ? 0.0 : item.getProduto().getDesconto() / 100) * item.getQuantidade()
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
        if(StringUtils.isBlank(pedido.getNomeUsuario())){
          pedido.setNomeUsuario(pedido.getUsuario().getNome());
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

  public PedidoCardInfoResponseDTO transformarPedidoCardInfo(Pedido pedido) {
    PedidoCardInfoResponseDTO response = new PedidoCardInfoResponseDTO();

    response.setDataPedido(DateFormat.formatToCustomPattern(pedido.getDataPedido()));
    response.setDataEntrega(DateFormat.format(pedido.getDataEntrega(), "dd/MM/yyyy"));
    response.setId(pedido.getId());
    response.setValorTotal(pedido.getValorTotal());
    response.setEmailCliente(pedido.getUsuario().getEmail());
    response.setNomeUsuario(pedido.getNomeUsuario());
    response.setTipoCliente(pedido.getUsuario().getRole().toString());
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
      double descontoProduto = item.getDesconto() == null ? 0.0 : item.getDesconto() / 100;
      double precoComDesconto = precoProduto - (precoProduto * descontoProduto);


      double totalPersonalizacoes = 0.0;

      if (item.getId() != null) {
        ItemPedido itemPedido = itemPedidoRepository.findById(item.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado")
        );

        totalPersonalizacoes = itemPedido.getPersonalizacoes().stream().mapToDouble(
                personalizacao -> personalizacao.getOpcaoPersonalizacao().getAcrescimoOpcao()
        ).sum();

      } else {
        totalPersonalizacoes = item.getPersonalizacoes().stream().mapToDouble(
                personalizacao -> personalizacao.getValorPersonalizacao()
        ).sum();
      }


      return (precoComDesconto + totalPersonalizacoes) * item.getQuantidade();
    }).sum() + pedido.getValorFrete();
  }

  /**
   * Calcula o valor total dos itens do pedido aplicando o desconto apenas sobre o valor do produto
   * e somando separadamente o valor das personalizações.
   *
   * <p>Para cada item no pedido, o cálculo segue esta fórmula:</p>
   *
   * <pre>
   *   valor_total_item = (preco_produto_com_desconto * quantidade) + (soma_personalizacoes * quantidade)
   * </pre>
   * <p>
   * Onde:
   * - <b>preco_produto_com_desconto</b> = precoProduto - (precoProduto * (desconto / 100))
   * - <b>soma_personalizacoes</b> = soma de todos os valores de personalização aplicados ao item
   * - <b>quantidade</b> = quantidade do produto no pedido
   *
   * <p>Importante: o valor das personalizações <b>não é afetado pelo desconto</b>.</p>
   *
   * @param itens Lista de itens do pedido com seus respectivos produtos e personalizações
   * @return Valor total do pedido, somando os produtos com desconto e personalizações
   */
  private Double calcularValorTotalDesconto(List<ItemPedidoRequestDTO> itens) {
    return itens.stream().mapToDouble(item -> {
      ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(item.getFkProduto());

      double precoProduto = produto.getPreco();
      double descontoProduto = item.getValorDesconto()  == null ? 0.0 : item.getValorDesconto() / 100;
      double precoComDesconto = precoProduto - (precoProduto * descontoProduto);

      double totalPersonalizacoes = item.getPersonalizacoes()
              .stream().mapToDouble(PersonalizacaoItemPedidoRequestDTO::getValorPersonalizacao).sum() * item.getQuantidade();

      return (precoComDesconto * item.getQuantidade()) + totalPersonalizacoes;
    }).sum();
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

  public ResponseEntity<PedidoRevisaoResponseDTO> gerarRevisaoPedido(PedidoRequestDTO novoPedido) {
    PedidoRevisaoResponseDTO revisao = new PedidoRevisaoResponseDTO();

    Double valorTotalVenda = novoPedido.getItens().stream().mapToDouble(i -> {
      Double desconto = i.getDesconto();
      Produto produto = produtoService.buscarEntidadeProdutoPorId(i.getFkProduto());
      double precoBase = produto.getPreco() * i.getQuantidade();
      double valorDesconto = precoBase * (desconto / 100);

      double totalPersonalizacao = 0.0;
      if (i.getPersonalizacoes() != null) {
        totalPersonalizacao = i.getPersonalizacoes().stream().mapToDouble(p -> p.getValorPersonalizacao()).sum();
      }

      return (precoBase - valorDesconto) + totalPersonalizacao;
    }).sum();

    Double custoMaterial = novoPedido.getItens().stream().mapToDouble(i -> {
      Produto produto = produtoService.buscarEntidadeProdutoPorId(i.getFkProduto());
      return produto.getMateriaisProduto().stream().mapToDouble(m -> m.getMaterial().getPrecoUnitario() * m.getQtdMaterialNecessario()).sum() * i.getQuantidade();
    }).sum();

    Double lucroReais = valorTotalVenda - custoMaterial;

    Double lucroPercentual = (lucroReais / valorTotalVenda) * 100;

    Double valorFreteTotal = novoPedido.getValorFrete();

    revisao.setValorVenda(valorTotalVenda);
    revisao.setCustoMaterial(custoMaterial);
    revisao.setLucroReais(lucroReais);
    revisao.setLucroPercentual(lucroPercentual);
    revisao.setValorFreteTotal(valorFreteTotal);
    revisao.setStatusPedido(StatusPedido.valueOf(novoPedido.getStatusPedido()));
    revisao.setDataConclusao(String.valueOf(novoPedido.getDataConclusao()));
    revisao.setDataPedido(novoPedido.getDataPedido().toString());
    revisao.setNomeCliente(novoPedido.getCliente());
    revisao.setTelefoneCliente(novoPedido.getTelefoneCliente());
    revisao.setEmailCliente(novoPedido.getEmailCliente());
    revisao.setEnderecoEntrega(enderecoMapper.toEnderecoResponseDTO(novoPedido.getEnderecoEntrega()));

    revisao.setItens(new ArrayList<>());

    revisao.getItens().addAll(novoPedido.getItens().stream().map(i -> {
      ItemPedidoResponseDTO item = new ItemPedidoResponseDTO();
      item.setId(i.getId());
      item.setProduto(produtoService.buscarProdutoPorId(i.getFkProduto()));
      item.setQuantidade(i.getQuantidade());
      item.setValor(i.getValor());
      item.setValorTotal(i.getValorTotal());
      item.setCustoProducao(i.getCustoProducao());
      item.setDesconto(i.getDesconto());
      return item;
    }).toList());

    revisao.setFormaPagamento(novoPedido.getFormaPgto());
    revisao.setOrigemPedido(novoPedido.getOrigemPedido());
    revisao.setObservacao(novoPedido.getObservacao());
    revisao.setDataEnvio(novoPedido.getDataVenda());
    revisao.setTipoUsuario(novoPedido.getTipoUsuario());
    return ResponseEntity.ok().body(revisao);
  }

  private Double calcularValorTotalProducaoItem(ItemPedidoRequestDTO item) {
    Produto produto = produtoService.buscarEntidadeProdutoPorId(item.getFkProduto());
    return produto.getMateriaisProduto().stream().mapToDouble(mp -> {
      Material material = mp.getMaterial();
      return material.getPrecoUnitario() * mp.getQtdMaterialNecessario();
    }).sum();
  }

  private void vailidarPedidoManual(PedidoRequestDTO novoPedido) {
    if (novoPedido == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser nulo");
    }

    if (novoPedido.getTipoUsuario() == null) {
      novoPedido.setTipoUsuario(UserRole.STATIC_USER);
    }

    if (novoPedido.getIdCliente() == null &&
            List.of(UserRole.USER, UserRole.ADMIN).contains(novoPedido.getTipoUsuario())) {
      if (novoPedido.getTipoUsuario().equals(UserRole.ADMIN)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuários ADMINS não podem ter pedidos!");
      }

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuários sem id!");
    }

    if (novoPedido.getItens() == null || novoPedido.getItens().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O pedido não contém itens!");
    }
  }

  public ResponseEntity cadastrarPedidoManual(PedidoRequestDTO novoPedido) {
    vailidarPedidoManual(novoPedido);

    Usuario cliente = new Usuario();
    Pedido pedido = new Pedido();

    if (novoPedido.getIdCliente() != null) {
      cliente = userRepository.findById(novoPedido.getIdCliente()).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
      );
      pedido.setNomeUsuario(cliente.getNome());
    } else {
      if (userRepository.existsByEmail(novoPedido.getEmailCliente())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente já existe com esse e-mail");
      }

      cliente.setNome(novoPedido.getCliente());
      cliente.setEmail(novoPedido.getEmailCliente());
      cliente.setTelefone(novoPedido.getTelefoneCliente());
      cliente.setSenha(UUID.randomUUID().toString());
      cliente.setRole(UserRole.STATIC_USER);
      cliente.setStatus(Status.HABILITADO);
      pedido.setNomeUsuario(cliente.getNome());
    }

    Endereco endereco = enderecoMapper.toEndereco(novoPedido.getEnderecoEntrega());

    if (endereco.getId() == null && cliente.getRole().equals(UserRole.USER)) {
      return ResponseEntity.badRequest().body("Endereços novos não podem ser vinculados a usuários do e-commerce!");
    } else if (endereco.getId() == null && cliente.getRole().equals(UserRole.STATIC_USER)) {
      endereco.setUsuario(cliente);
      cliente.setEnderecos(new ArrayList<>());
      cliente.getEnderecos().add(endereco);
    } else {
      endereco = enderecoRepository.findById(endereco.getId()).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado")
      );
    }

    pedido.setEnderecoEntrega(endereco);

    LocalDate data = LocalDate.parse(novoPedido.getDataPedido());
    pedido.setDataPedido(data.atStartOfDay());

    if (StatusPedido.CONCLUIDO.equals(novoPedido.getStatusPedido())) {
      pedido.setDataConclusao(novoPedido.getDataConclusao().atStartOfDay());
    }

    if (!List.of(
            StatusPedido.CARRINHO, StatusPedido.PENDENTE_PAGAMENTO
    ).contains(StatusPedido.valueOf(novoPedido.getStatusPedido()))) {
      if (StringUtils.isBlank(novoPedido.getDataVenda())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de venda é obrigatória para pedidos que não estão no carrinho ou pendentes de pagamento");
      }
      LocalDate dataVenda = LocalDate.parse(novoPedido.getDataVenda());
      pedido.setDataPagamento(dataVenda.atStartOfDay());
    }

    pedido.setStatus(StatusPedido.valueOf(novoPedido.getStatusPedido()));
    pedido.setOrigemPedido(novoPedido.getOrigemPedido());
    pedido.setTelefoneCliente(cliente.getTelefone());
    pedido.setFormaPgto(novoPedido.getFormaPgto());
    pedido.setValorFrete(novoPedido.getValorFrete());
    pedido.setValorDesconto(calcularValorTotalDesconto(novoPedido.getItens()));
    pedido.setValorTotal(calcularValorTotalPedidoAtualizado(novoPedido));
    List<ItemPedido> itens = new ArrayList<>();

    novoPedido.getItens().forEach(i -> {
      ItemPedido item = new ItemPedido();
      Produto produto = produtoService.buscarEntidadeProdutoPorId(i.getFkProduto());
      item.setProduto(produto);
      item.setQuantidade(i.getQuantidade());

      item.setValor(produto.getPreco());

      item.setValorTotal(calcularValorTotalDesconto(List.of(i)));
      item.setCustoProducao(calcularValorTotalProducaoItem(i));
      item.setDesconto(i.getDesconto());
      item.setPersonalizacoes(new ArrayList<>());
      item.setProdutoFeito(i.getProdutoFeito());
      item.setPedido(pedido);
      if (i.getPersonalizacoes() != null) {
        i.getPersonalizacoes().forEach(p -> {
          PersonalizacaoItemPedido personalizacao = new PersonalizacaoItemPedido();
          Personalizacao personalizacaoEntidade = personalizacaoRepository.findById(p.getFkPersonalizacao()).orElseThrow(
                  () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personalização não encontrada")
          );
          OpcaoPersonalizacao opcao = personalizacaoEntidade.getOpcoes().stream().filter(
                  opcaoPersonalizacao -> opcaoPersonalizacao.getIdOpcaoPersonalizacao() == p.getFkOpcaoPersonalizacao()
          ).findFirst().orElseThrow(
                  () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção de personalização não encontrada")
          );
          personalizacao.setOpcaoPersonalizacao(opcao);
          personalizacao.setPersonalizacao(personalizacaoEntidade);
          personalizacao.setValorPersonalizacao(opcao.getAcrescimoOpcao());
          personalizacao.setItemPedido(item);
          personalizacao.setDescricaoPersonalizacao(p.getDescricaoPersonalizacao());
          item.getPersonalizacoes().add(personalizacao);
        });
      }

      itens.add(item);
    });

    if (novoPedido.getIdsResponsaveis() != null && !novoPedido.getIdsResponsaveis().isEmpty()) {
      pedido.setResponsaveis(new ArrayList<>());
      novoPedido.getIdsResponsaveis().forEach(r -> {
        ResponsavelPedido responsavel = new ResponsavelPedido();
        responsavel.setResponsavel(
                userRepository.findById(r).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsável não encontrado"))
        );
        responsavel.setPedido(pedido);
        pedido.getResponsaveis().add(responsavel);
      });
    }

    if (novoPedido.getPrazoEntrega() == null) {
      novoPedido.setPrazoEntrega(0);
    }

    pedido.setDataEntrega(pedido.getDataPedido().plusDays(novoPedido.getPrazoEntrega()));

    pedido.setUsuario(cliente);

    pedido.setItens(itens);
    Pedido pedidoSalvo = repository.save(pedido);

    return ResponseEntity.status(201).body(mapper.pedidoToPedidoResponseDTO(pedidoSalvo).getItens());
  }

  public void updateStatus(Integer idPedido) {

    Pedido pedidoBanco = repository.findById(idPedido).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    StatusPedido status = pedidoBanco.getStatus();

    switch (status) {
      case PENDENTE_PAGAMENTO:
        status = StatusPedido.PENDENTE;
        pedidoBanco.setDataPagamento(LocalDateTime.now());
        break;
      case PENDENTE:
        status = StatusPedido.EM_PREPARO;
        break;
      case EM_PREPARO:
        status = StatusPedido.EM_ROTA;
        break;
      case EM_ROTA:
        status = StatusPedido.CONCLUIDO;
        pedidoBanco.setDataConclusao(LocalDateTime.now());
        break;
      default:
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser atualizado");
//        break;
    }

    pedidoBanco.setStatus(status);

    repository.save(pedidoBanco);
  }
}
