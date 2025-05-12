package tcatelie.microservice.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("clausilvaaraujo11@gmail.com");

        String emailContent = buildEmailContent(variables, "email-template");

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    public void sendForgotPasswordEmail(String to, String resetLink) throws MessagingException {
        Map<String, Object> variables = Map.of("resetLink", resetLink);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Redefinição de Senha");
        helper.setFrom("clausilvaaraujo11@gmail.com");

        String emailContent = buildEmailContent(variables, "forgot-password-template");

        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    private String buildEmailContent(Map<String, Object> variables, String templateName) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}
