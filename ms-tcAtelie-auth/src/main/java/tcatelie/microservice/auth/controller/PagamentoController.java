package tcatelie.microservice.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import tcatelie.microservice.auth.dto.request.PagamentoApiExternaDto;
import tcatelie.microservice.auth.dto.request.PagamentoDto;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PagamentoRepository;
import tcatelie.microservice.auth.strategy.CreditoStrategy;
import tcatelie.microservice.auth.strategy.DebitoStrategy;
import tcatelie.microservice.auth.strategy.GerenciadorPagamento;
import tcatelie.microservice.auth.strategy.PixStrategy;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private static final Logger log = LoggerFactory.getLogger(PagamentoController.class);
    @Autowired
    private PagamentoRepository repository;

    @GetMapping
    public ResponseEntity<PagamentoDto[]> listarMetodosPagamento(){
        RestClient client = RestClient.builder()
                .baseUrl("https://api.mercadopago.com")
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                .build();

        String token = "TEST-4975778094534711-090616-29a906e68511ec07a7b50afa84f351ba-258512670";

        String raw = client.get()
                .uri("/v1/payment_methods")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(String.class);

        log.info("Resposta da API: " + raw);

        List<PagamentoApiExternaDto> pagamentos = client.get()
                .uri("/v1/payment_methods")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (pagamentos == null) {
            return ResponseEntity.noContent().build();
        }

        List<PagamentoDto> resposta =
                pagamentos.stream().map(item -> {
                    PagamentoDto pagamentoDto = new PagamentoDto();
                    pagamentoDto.setNome(item.getNome());
                    pagamentoDto.setTipoPagamento(item.getTipoPagamento());
                    pagamentoDto.setStatus(item.getStatus());
                    return pagamentoDto;
                }).toList();

        PagamentoDto[] pagamentosVetor = new PagamentoDto[resposta.size()];

        resposta.toArray(pagamentosVetor);
        GerenciadorPagamento.ordenarVetorPorOrdemAlfabetica(pagamentosVetor);

        return ResponseEntity.ok(pagamentosVetor);
    }

    @PostMapping("/credito")
    public ResponseEntity<Pedido> realizarPedidoCredito(
            @RequestBody Pedido pedido
            ){
        CreditoStrategy creditoStrategy = new CreditoStrategy();
        pedido = creditoStrategy.pagar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pedido));
    }

    @PostMapping("/debito")
    public ResponseEntity<Pedido> realizarPedidoDebito(
            @RequestBody Pedido pedido
    ){
        DebitoStrategy debitoStrategy = new DebitoStrategy();
        pedido = debitoStrategy.pagar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pedido));
    }

    @PostMapping("/pix")
    public ResponseEntity<Pedido> realizarPedido(
            @RequestBody Pedido pedido
    ){
        PixStrategy pixStrategy = new PixStrategy();
        pedido = pixStrategy.pagar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pedido));
    }
}
