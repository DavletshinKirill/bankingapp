package dev.davletshin.dossier.service;


import dev.davletshin.shared.dto.EmailMessageDTO;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailService {
    void sendEmail(EmailMessageDTO emailMessage) throws MessagingException, TemplateException, IOException;
}
