package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.PedidoResponseDTO;
import tcatelie.microservice.auth.dto.filter.PedidoFiltroDTO;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.mapper.PedidoMapper;
import tcatelie.microservice.auth.service.ExcelService;
import tcatelie.microservice.auth.service.PedidoService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedido", description = "Operações relacionadas a pedidos")
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final ExcelService excelService;
    private final Logger LOGGER = LoggerFactory.getLogger(PedidoController.class);

    @Operation(summary = "Busca um pedido pelo id",
            description = "Retorna um pedido pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
            })
    @GetMapping("{idPedido}")
    public ResponseEntity getPedidoById(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(service.transformarPedido(service.getPedidoById(idPedido)));
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

    @Operation(summary = "lista todos os pedidos",
            description = "listao pedido",
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

    @GetMapping("/carrinho/{idCliente}")
    public ResponseEntity getPedidoCarrinho(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.carregarCarrinhoUsuario(idCliente));
    }

    @GetMapping("/{idCliente}/ultimo")
    public ResponseEntity buscarUltimoPedidoCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok().body(service.listarUltimoPedido(idCliente));
    }

    @PutMapping("/{id}/codigo-rastreio")
    public ResponseEntity atualizarCodigoRastreio(@PathVariable Integer id, @RequestBody PedidoRequestDTO request) {
        return ResponseEntity.ok(service.atualizarCodigoRastreio(id, request.getCodigoRastreio()));
    }

    @GetMapping("/export")
    public void exportPedidosToExcel(HttpServletResponse response,
                                     @RequestParam(value = "idPedido", required = false) Integer idPedido,
                                     @RequestParam(value = "nomeCliente", required = false) String nomeCliente,
                                     @RequestParam(value = "idResponsavel", required = false) Integer idResponsavel,
                                     @RequestParam(value = "status", required = false) String status) {
        LOGGER.info("Gerando arquivo Excel com pedidos...");
        PedidoFiltroDTO filtro = new PedidoFiltroDTO();

        filtro.setIdPedido(idPedido);
        filtro.setNomeCliente(nomeCliente);

        LocalDateTime startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(23, 59, 59);

        filtro.setDataPedidoInicio(startOfWeek);
        filtro.setDataPedidoFim(endOfWeek);

        if (idResponsavel != null) {
            filtro.setIdsResponsaveis(List.of(idResponsavel));
        }

        if (status != null && !status.isEmpty()) {
            filtro.setStatusList(List.of(status));
        }

        List<PedidoResponseDTO> pedidos = service.findAll(filtro);
        excelService.gerarArquivoPedidosExcel(response, pedidos);
    }

}
