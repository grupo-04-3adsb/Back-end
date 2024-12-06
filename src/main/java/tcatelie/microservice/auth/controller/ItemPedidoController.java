package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.ItemPedidoRequestDTO;
import tcatelie.microservice.auth.service.ItemPedidoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item-pedidos")
@Tag(name = "ItemPedido", description = "ItemPedido API | Lidar com os itens do pedido, adicionar ao carrinho etc...")
public class ItemPedidoController {

    private final ItemPedidoService service;

    @PostMapping("{idUsuario}")
    public ResponseEntity adicionarAoCarrinho(@PathVariable Integer idUsuario,
                                              @RequestBody @Valid ItemPedidoRequestDTO itemPedidoRequestDTO) {

        return ResponseEntity.ok().body(service.adicionarAoCarrinho(
                idUsuario, itemPedidoRequestDTO
        ));
    }

    @PutMapping("{idItemPedido}")
    public ResponseEntity concluirItemPedido(@PathVariable Integer idItemPedido) {
        service.concluirItemPedido(idItemPedido);

        return ResponseEntity.ok().body("Item concluído.");
    }

    @DeleteMapping("{idItemPedido}")
    public ResponseEntity removerItemPedido(@PathVariable Integer idItemPedido) {
        service.removerItemPedido(idItemPedido);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Alterar quantidade do item pedido",
            description = "Altera a quantidade do item pedido.",
            tags = {"ItemPedido"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Quantidade a ser alterada.",
                    required = true
            )
    )
    @PutMapping("{idItemPedido}/quantidade")
    public ResponseEntity alterarQuantidade(@PathVariable Integer idItemPedido,
                                            @RequestParam Integer quantidade) {
        service.alterarQuantidade(idItemPedido, quantidade);

        return ResponseEntity.ok().body("Quantidade alterada.");
    }
}
