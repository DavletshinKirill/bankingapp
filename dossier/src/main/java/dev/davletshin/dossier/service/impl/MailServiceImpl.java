package dev.davletshin.dossier.service.impl;


import dev.davletshin.dossier.service.MailService;
import dev.davletshin.shared.dto.EmailMessageDTO;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Override
    public void sendEmail(EmailMessageDTO emailMessage) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                true,
                "UTF-8");
        helper.setSubject(emailMessage.getTheme().getEmailTitle());
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
        model.put("name", emailMessage.getText());
        configuration.getTemplate(emailMessage.getTheme().getTopicTitle() + ".ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }
}
