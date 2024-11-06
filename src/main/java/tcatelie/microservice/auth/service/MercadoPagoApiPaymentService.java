package tcatelie.microservice.auth.service;

import com.mercadopago.*;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class MercadoPagoApiPaymentService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public Payment processPayment(BigDecimal amount, String token, String email) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken("YOUR_ACCESS_TOKEN");

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(amount)
                .token(token)
                .description("Payment description")
                .installments(1)
                .paymentMethodId("visa")
                .payer(PaymentPayerRequest.builder().email(email).build())
                .build();

        return client.create(createRequest);
    }

    public Payment processPaymentWithCustomHeaders(PaymentCreateRequest createRequest, String customAccessToken) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", "...");

        MPRequestOptions requestOptions =
                MPRequestOptions.builder()
                        .accessToken(customAccessToken)
                        .connectionRequestTimeout(2000)
                        .connectionTimeout(2000)
                        .socketTimeout(2000)
                        .customHeaders(customHeaders)
                        .build();

        return client.create(createRequest, requestOptions);
    }
}