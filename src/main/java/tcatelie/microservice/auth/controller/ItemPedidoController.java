package tcatelie.microservice.auth.controller;

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
        service.adicionarAoCarrinho(
                idUsuario, itemPedidoRequestDTO
        );

        return ResponseEntity.ok().body("Item adicionado ao carrinho.");
    }
}
