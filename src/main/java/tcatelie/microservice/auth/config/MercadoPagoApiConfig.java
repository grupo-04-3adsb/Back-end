package tcatelie.microservice.auth.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.logging.Level;

@Configuration
public class MercadoPagoApiConfig {

    @PostConstruct
    public void configureMercadoPago() {
        MercadoPagoConfig.setConnectionRequestTimeout(2000);
        MercadoPagoConfig.setSocketTimeout(2000);

        MercadoPagoConfig.setLoggingLevel(Level.FINEST);
    }
}
