package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.dto.response.CustoOutrosResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.model.CustoOutros;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;
import tcatelie.microservice.auth.specification.PedidoSpecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final CustoOutrosService custoOutrosService;

    private List<CustoOutrosResponseDTO> custosOutros = new ArrayList<>();

    public Pedido getPedidoById(Integer idPedido) {
        return repository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
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

        pedido.setStatus(StatusPedido.valueOf(pedidoRequestDTO.getStatusPedido()));

        if (pedido.getStatus().equals(StatusPedido.CONCLUIDO)) {
            pedido.setDataConclusao(LocalDateTime.now());
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

        if(custosOutros.isEmpty()){
            custosOutros = custoOutrosService.findAll();
        }

        if (pedido.getStatus().equals(StatusPedido.PENDENTE) ||
                pedido.getStatus().equals(StatusPedido.PENDENTE_PAGAMENTO)) {

            double totalCustoProducao = pedido.getItens().stream()
                    .mapToDouble(item -> item.getProduto().getMateriaisProduto().stream()
                            .mapToDouble(materialProduto ->
                                    materialProduto.getMaterial().getPrecoUnitario() *
                                            materialProduto.getQtdMaterialNecessario())
                            .sum() * item.getQuantidade())
                    .sum();

            double totalCustoOutros = pedido.getItens().stream()
                    .mapToDouble(item -> custosOutros.stream()
                            .mapToDouble(outroCusto -> outroCusto.getValor())
                            .sum() * item.getQuantidade())
                    .sum();

            response.setTotalCustoProducao(totalCustoProducao);
        }

        response.setDataPedido(pedido.getDataPedido().toString());

        return response;
    }

}
