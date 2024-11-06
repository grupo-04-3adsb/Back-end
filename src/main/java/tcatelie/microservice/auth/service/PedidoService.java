package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;

    public PedidoResponseDTO getPedidoById(Integer idPedido) {

        Optional<Pedido> pedido = repository.findById(idPedido);

        if (pedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        return mapper.pedidoToPedidoResponseDTO(pedido.get());

    }

    public Page<PedidoResponseDTO> getPedidos(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(mapper::pedidoToPedidoResponseDTO);
    }

    public List<Pedido> buscarPedidosPorStatus(StatusPedido status) {
        return repository.findByStatus(status);
    }

    public void atualizarStatusPedido(Pedido pedido, StatusPedido novoStatus) {
        pedido.setStatus(novoStatus);
        repository.save(pedido);
    }
}
