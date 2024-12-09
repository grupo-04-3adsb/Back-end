package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tcatelie.microservice.auth.dto.request.PedidoRequestDTO;
import tcatelie.microservice.auth.service.CalculoFreteApiService;

@RestController
@RequestMapping("/calcular-fretes")
@RequiredArgsConstructor
@Tag(name = "Calcular Fretes", description = "API para cálculo de fretes")
public class CalculoFreteController {

    private final CalculoFreteApiService service;

    @GetMapping("/pedido/{id}")
    public ResponseEntity calcularFretePedido(@PathVariable Integer id) {
        return service.calcularFretePedido(id);
    }

    @Operation(
            summary = "Calcular frete por CEP",
            description = "Calcula o frete para um determinado CEP",
            tags = {"Calcular Fretes"}
    )
    @PostMapping("/{cep}")
    public ResponseEntity calcularFreteCep(@PathVariable String cep, @RequestBody PedidoRequestDTO carrinho) {
        return service.calcularFrete(cep, carrinho);
    }


}
