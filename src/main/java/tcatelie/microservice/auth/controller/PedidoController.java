package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedido", description = "Operações relacionadas a pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;

    @Operation(summary = "Busca um pedido pelo id",
            description = "Retorna um pedido pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            })
    @GetMapping("{idPedido}")
    public ResponseEntity getPedidoById(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(mapper.pedidoToPedidoResponseDTO(service.getPedidoById(idPedido)));
    }

    @Operation(summary = "Busca todos os pedidos",
            description = "Retorna todos os pedidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição")
            })
    @GetMapping
    public Page<PedidoResponseDTO> getPedidos(@RequestParam(value = "page", defaultValue = "0") Integer
                                                      page, @RequestParam(value = "size", defaultValue = "10") Integer size,
                                              @RequestParam(value = "sort", defaultValue = "id") String sortBy,
                                              @RequestParam(value = "direction", defaultValue = "ASC") String sortOrder
    ) {
        return service.getPedidos(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy)));
    }

    @Operation(summary = "Cria um pedido",
            description = "Cria um pedido",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido criado"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição")
            })
    @GetMapping("/listar")
    public List<PedidoResponseDTO> findAll(
            @RequestParam(value = "idPedido", required = false) Integer idPedido,
            @RequestParam(value = "nomeCliente", required = false) String nomeCliente,
            @RequestParam(value = "idResponsavel", required = false) Integer idResponsavel,
            @RequestParam(value = "status", required = false) String status
    ) {
        PedidoFiltroDTO filtro = new PedidoFiltroDTO();
        filtro.setIdPedido(idPedido);
        filtro.setNomeCliente(nomeCliente);

        if (idResponsavel != null) {
            filtro.setIdsResponsaveis(List.of(idResponsavel));
        }

        if (status != null && !status.isEmpty()) {
            filtro.setStatusList(List.of(status));
        }

        return service.findAll(filtro);
    }

    @PutMapping("{idPedido}")
    public ResponseEntity updatePedido(@PathVariable Integer idPedido, @RequestBody PedidoRequestDTO pedido) {
        return service.updatePedido(idPedido, pedido);
    }
}
