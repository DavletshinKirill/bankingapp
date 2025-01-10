package dev.davletshin.dossier.service.impl;

import dev.davletshin.dossier.dto.EmailMessageDTO;
import dev.davletshin.dossier.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration configuration;


    @Override
    public void sendEmail(EmailMessageDTO emailMessage) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                false,
                "UTF-8");
        helper.setSubject("Thank you for registration, " + emailMessage.getAddress());
        helper.setTo(emailMessage.getAddress());
        String emailContent = getRegistrationEmailContent(emailMessage);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }


    private String getRegistrationEmailContent(
            final EmailMessageDTO emailMessage
    ) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", emailMessage.getAddress());
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }
}
