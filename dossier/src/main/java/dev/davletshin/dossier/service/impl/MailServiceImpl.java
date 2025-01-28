package dev.davletshin.dossier.service.impl;


import dev.davletshin.dossier.dto.EmailMessageDTO;
import dev.davletshin.dossier.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Override
    @Async
    public void sendEmail(EmailMessageDTO emailMessage) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                true,
                "UTF-8");
        helper.setSubject(emailMessage.getTheme().getEmailTitle());
        helper.setTo(emailMessage.getAddress());
        String emailContent = getEmailContent(emailMessage);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
        log.info("Email sent to {}", emailMessage.getAddress());
    }


    private String getEmailContent(
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
