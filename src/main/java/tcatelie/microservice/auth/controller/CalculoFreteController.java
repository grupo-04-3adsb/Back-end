package tcatelie.microservice.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
