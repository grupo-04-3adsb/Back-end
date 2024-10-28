package tcatelie.microservice.auth.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class MercadoPagoService {

    private final Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);
    private final PedidoRepository pedidoRepository;

    public MercadoPagoService(@Value("${mercadopago.access.token}") String accessToken ,PedidoRepository pedidoRepository) {
        MercadoPagoConfig.setAccessToken(accessToken);
        this.pedidoRepository = pedidoRepository;
    }

    public String criarPagamento(Integer idPedido)
            throws MPException, MPApiException {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();

        pedido.getItens().forEach(itemPedido -> {
            PreferenceItemRequest item =
                    PreferenceItemRequest.builder()
                            .title(itemPedido.getProduto().getNome())
                            .quantity(itemPedido.getQuantidade())
                            .unitPrice(BigDecimal.valueOf(itemPedido.getValor()))
                            .build();
            items.add(item);
        });

        PreferenceRequest request = PreferenceRequest.builder()
                .purpose("wallet_purchase")
                .items(items).build();

        var response =  client.create(request);
        logger.info("Payment response: {}", response);
        return response.getId().toString();
    }

    public String criarPagamentoPix(Integer idPedido) throws MPException, MPApiException {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(BigDecimal.valueOf(pedido.getValorTotal()))
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(pedido.getUsuario().getEmail())
                                .firstName(pedido.getUsuario().getNome())
                                .build()
                )
                .build();

        var paymentResponse = client.create(paymentCreateRequest, requestOptions);
        logger.info("Pagamento PIX criado com sucesso: {}", paymentResponse.getId());

        return paymentResponse.getId().toString();
    }
}

