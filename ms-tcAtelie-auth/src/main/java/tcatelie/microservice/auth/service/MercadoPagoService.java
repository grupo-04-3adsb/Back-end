package tcatelie.microservice.auth.service;

import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.MercadoPagoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    private final Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);

    public MercadoPagoService(@Value("${mercadopago.access.token}") String accessToken) {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String criarPreferencia() throws MPException, MPApiException {
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id("item-ID-1234")
                .title("Meu produto")
                .currencyId("BRL")
                .pictureUrl("https://www.mercadopago.com/org-img/MP3/home/logomp3.gif")
                .description("Descrição do Item")
                .categoryId("art")
                .quantity(1)
                .unitPrice(new BigDecimal("75.76"))
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("João")
                .surname("Silva")
                .email("user@email.com")
                .phone(PhoneRequest.builder().areaCode("11").number("4444-4444").build())
                .identification(IdentificationRequest.builder().type("CPF").number("19119119100").build())
                .address(AddressRequest.builder()
                        .streetName("Street")
                        .streetNumber(String.valueOf(123))
                        .zipCode("06233200")
                        .build())
                .build();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://www.success.com")
                .failure("http://www.failure.com")
                .pending("https://www.pending.com")
                .build();

        List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
        excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("bolbradesco").build());
        excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("pec").build());

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("debit_card").build());

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentMethods(excludedPaymentMethods)
                .excludedPaymentTypes(excludedPaymentTypes)
                .installments(12)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .payer(payer)
                .backUrls(backUrls)
                .autoReturn("approved")
                .paymentMethods(paymentMethods)
                .notificationUrl("https://www.your-site.com/ipn")
                .statementDescriptor("MEUNEGOCIO")
                .externalReference("Reference_1234")
                .expires(true)
                .expirationDateFrom(OffsetDateTime.now(ZoneOffset.of("-03:00")))
                .expirationDateTo(OffsetDateTime.now(ZoneOffset.of("-03:00")).plusHours(5))
                .build();

        Preference preference = new PreferenceClient().create(preferenceRequest);
        logger.info("Preference ID: {}", preference.getId());
        return preference.getId();
    }
}
