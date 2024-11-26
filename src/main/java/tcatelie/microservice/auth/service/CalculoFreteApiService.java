package tcatelie.microservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.dto.request.api.melhorEnvio.*;
import tcatelie.microservice.auth.dto.response.api.melhorEnvio.ShippingResponseDTO;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalculoFreteApiService {

    private final RestTemplate restTemplate;

    @Value("${melhorenvio.api.url}")
    private String apiUrl;

    @Value("${melhorenvio.api.token}")
    private String apiToken;

    @Value("${postalcode.cep.default}")
    private String cepFrom;

    private final PedidoRepository pedidoRepository;

    public ResponseEntity calcularFretePedido(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));


        MelhorEnvioPedidoRequestDTO melhorEnvioPedidoRequestDTO = new MelhorEnvioPedidoRequestDTO();

        melhorEnvioPedidoRequestDTO.setFrom(new FromRequestDTO(cepFrom));
        melhorEnvioPedidoRequestDTO.setTo(new ToRequestDTO(pedido.getEnderecoEntrega().getCep()));
        melhorEnvioPedidoRequestDTO.setProducts(pedido.getItens().stream().map(
                item -> {
                    List<String> dimensao = Arrays.stream(item.getProduto().getDimensao().split("x")).toList();

                    return
                        new ProductRequestDTO(
                                item.getProduto().getId().toString(),
                                Integer.parseInt(dimensao.get(0)),
                                Integer.parseInt(dimensao.get(1)),
                                Integer.parseInt(dimensao.get(2)),
                                item.getProduto().getPeso(),
                                0.0,
                                item.getQuantidade()
                        );
                }
            ).toList()
        );

        melhorEnvioPedidoRequestDTO.setOptions(
                new OptionsRequestDTO(
                        false,
                        false
                )
        );

        melhorEnvioPedidoRequestDTO.setServices("");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("User-Agent", "clausilvaaraujo11@gmail.com");

        HttpEntity<MelhorEnvioPedidoRequestDTO> request = new HttpEntity<>(melhorEnvioPedidoRequestDTO, headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    apiUrl + "/v2/me/shipment/calculate",
                    HttpMethod.POST,
                    request,
                    Object.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao calcular frete: " + response.getBody());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao calcular frete: " + e.getMessage(), e);
        }
    }
}
