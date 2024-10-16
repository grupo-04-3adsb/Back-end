package tcatelie.microservice.auth.observer;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tcatelie.microservice.auth.model.Produto;
import tcatelie.microservice.auth.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class EmailNotificacao implements Observer {

    private String emailAdmin = "";
    private EmailService emailService;

    @Override
    public void update(String message, Produto produto) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Novo Produto Cadastrado");
        variables.put("produto", produto);
        variables.put("bodyText", message);

        try {
            emailService.sendEmail(emailAdmin, "Novo Produto Cadastrado no Sistema", variables);
            System.out.println("E-mail enviado com sucesso sobre o produto: " + produto.getNome());
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
