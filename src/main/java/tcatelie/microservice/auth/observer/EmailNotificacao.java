package tcatelie.microservice.auth.observer;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificacao implements Observer {

    private String emailAdmin = "";
    private EmailService emailService;
    private final Logger LOGGER = LoggerFactory.getLogger(EmailNotificacao.class);

    @Override
    public void update(String message, Produto produto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Novo Produto Cadastrado");
        variables.put("produto", produto);
        variables.put("bodyText", message);

        try {
            emailService.sendEmail(emailAdmin, "Novo Produto Cadastrado no Sistema", variables);
            LOGGER.info("E-mail enviado com sucesso sobre o produto: " + produto.getNome());
        } catch (MessagingException e) {
            LOGGER.warn("Erro ao enviar e-mail sobre o produto: " + produto.getNome());
        }
    }

    @Override
    public void update(String message) {

    }
}
