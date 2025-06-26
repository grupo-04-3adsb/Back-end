package tcatelie.microservice.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tcatelie.microservice.auth.model.ParametroGeral;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ParametroGeralService parametroGeralService;

    public void sendEmail(String to, String subject, Map<String, Object> variables) throws MessagingException {
        sendEmail(to, subject, variables, "email-template");
    }

    public void sendEmail(String to, String subject, Map<String, Object> variables, String templateName) throws MessagingException {
        ParametroGeral emailRemetente = parametroGeralService.findByName("EMAIL_PRINCIPAL");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(emailRemetente.getValor());

        String emailContent = buildEmailContent(variables, templateName);

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendForgotPasswordEmail(String to, String resetLink) throws MessagingException {
        Map<String, Object> variables = Map.of("resetLink", resetLink);
        sendEmail(to, "Redefinição de Senha", variables, "forgot-password-template");
    }

    public void sendNotaFiscalEmail(String to, String nomeCliente, String numeroPedido, String linkNotaFiscal, String nomeEmpresa) throws MessagingException {
        Map<String, Object> variables = Map.of(
                "nomeCliente", nomeCliente,
                "numeroPedido", numeroPedido,
                "linkNotaFiscal", linkNotaFiscal,
                "nomeEmpresa", nomeEmpresa,
                "ano", String.valueOf(java.time.Year.now().getValue())
        );

        sendEmail("clausilvaaraujo11@gmail.com", "Sua Nota Fiscal - Pedido " + numeroPedido, variables, "email-template-nf");
    }

    private String buildEmailContent(Map<String, Object> variables, String templateName) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}
