package tcatelie.microservice.auth.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tcatelie.microservice.auth.enums.StatusPedido;
import tcatelie.microservice.auth.model.Pedido;
import tcatelie.microservice.auth.repository.PedidoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class MercadoPagoService {

    private final Logger logger = LoggerFactory.getLogger(MercadoPagoService.class);
    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    @Value("${mercado.pago.env}")
    private String env;

    public MercadoPagoService(@Value("${mercadopago.access.token}") String accessToken, PedidoRepository pedidoRepository, PedidoService pedidoService) {
        MercadoPagoConfig.setAccessToken(accessToken);
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
    }

    public String criarPagamento(Integer idPedido)
            throws MPException, MPApiException {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.PENDENTE_PAGAMENTO)
                && !pedido.getStatus().equals(StatusPedido.CARRINHO)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não está pendente de pagamento");
        }

        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();

        pedido.getItens().forEach(itemPedido -> {
            BigDecimal valor = "TEST".equalsIgnoreCase(env)
                    ? BigDecimal.valueOf(0.01)
                    : BigDecimal.valueOf(itemPedido.getValor());

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(itemPedido.getProduto().getNome())
                    .quantity(itemPedido.getQuantidade())
                    .unitPrice(valor)
                    .build();

            items.add(item);
        });

        PreferenceRequest request = PreferenceRequest.builder()
                .purpose("wallet_purchase")
                .items(items).build();

        var response = client.create(request);
        logger.info("Payment response: {}", response);
        return response.getId().toString();
    }

    public String criarPagamentoPix(Integer idPedido) throws MPException, MPApiException {
        try {


            Pedido pedido = pedidoRepository.findById(idPedido)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

            Map<String, String> customHeaders = new HashMap<>();
            customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

            MPRequestOptions requestOptions = MPRequestOptions.builder()
                    .customHeaders(customHeaders)
                    .build();

            PaymentClient client = new PaymentClient();

            Double valorPedido = env.equalsIgnoreCase("TEST")
                    ? 0.01
                    : pedido.getValorTotal();

            PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                    .transactionAmount(
                            BigDecimal.valueOf(valorPedido).setScale(2, RoundingMode.HALF_UP)
                    )                    .paymentMethodId("pix")
                    .payer(
                            PaymentPayerRequest.builder()
                                    .email(pedido.getUsuario().getEmail())
                                    .firstName(pedido.getUsuario().getNome())
                                    .build()
                    )
                    .externalReference(pedido.getId().toString())
                    .build();

            var paymentResponse = client.create(paymentCreateRequest, requestOptions);

            String paymentId = paymentResponse.getId().toString();
            logger.info("Pagamento PIX criado com sucesso: {}", paymentId);
            pedido.setStatus(StatusPedido.PENDENTE_PAGAMENTO);
            pedido.setPaymentId(paymentId);
            pedidoRepository.save(pedido);

            return paymentResponse.getId().toString();
        } catch (MPApiException e) {
            logger.error("Erro ao criar pagamento PIX: Código de erro: {}, Mensagem: {}, Conteúdo da resposta: {}",
                    e.getStatusCode(), e.getMessage(), e.getApiResponse().getContent());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Tivemos um problema ao criar o pagamento com PIX, tente novamente mais tarde.");
        }
    }

    public void atualizarPedido(String idPagamento){
        try{
            boolean pago = verificarPagamento(idPagamento);
            Pedido pedido = pedidoRepository.findByPaymentId(idPagamento)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

            if (pago) {
                logger.info("Pagamento do pedido {} foi confirmado", pedido.getId());
                pedidoService.atualizarStatusPedido(pedido, StatusPedido.PENDENTE);
            } else {
                pedidoService.atualizarStatusPedido(pedido, StatusPedido.PENDENTE_PAGAMENTO);
            }
        } catch (ResponseStatusException e) {
            logger.error("Erro ao atualizar pedido: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao atualizar pedido: {}", e.getMessage());
        }
    }

    public boolean verificarPagamento(String idPagamento) {
        PaymentClient paymentClient = new PaymentClient();
        try {

            if (idPagamento == null) {
                logger.error("ID de pagamento nulo");
                return false;
            }

            var payment = paymentClient.get(Long.valueOf(idPagamento));
            String status = payment.getStatus();

            if ("approved".equalsIgnoreCase(status)) {
                logger.info("Pagamento aprovado para o ID de pagamento: {}", idPagamento);
                return true;
            } else if ("pending".equalsIgnoreCase(status)) {
                logger.info("Pagamento pendente para o ID de pagamento: {}", idPagamento);
            } else if ("in_process".equalsIgnoreCase(status)) {
                logger.info("Pagamento em processo para o ID de pagamento: {}", idPagamento);
            } else if ("rejected".equalsIgnoreCase(status)) {
                logger.warn("Pagamento rejeitado para o ID de pagamento: {}", idPagamento);
            } else {
                logger.warn("Status de pagamento desconhecido ({}) para o ID de pagamento: {}", status, idPagamento);
            }

            return false;
        } catch (MPException | MPApiException e) {
            logger.error("Erro ao verificar pagamento para o ID de pagamento {}: {}", idPagamento, e.getMessage());
            return false;
        }
    }

}

