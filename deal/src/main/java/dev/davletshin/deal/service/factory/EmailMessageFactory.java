package dev.davletshin.deal.service.factory;


import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.enums.Theme;
import dev.davletshin.deal.web.dto.EmailMessageDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailMessageFactory {
    public EmailMessageDTO createEmailMessage(Client client, UUID statementUUID, Theme theme, UUID sesCode) {
        String text = getEmailTExt(theme, client, sesCode);
        return new EmailMessageDTO(client.getEmail(), statementUUID, text, theme);
    }

    private String getEmailTExt(Theme theme, Client client, UUID sesCode) {
        String body;
        switch (theme) {
            case FINISH_REGISTRATION, SEND_DOCUMENTS, CREDIT_ISSUED -> body = client.getFirstName() + " " +
                    client.getLastName() + " " + client.getMiddleName();
            case SEND_SES -> body = sesCode.toString();
            default -> throw new IllegalStateException("Unexpected value: " + theme);
        }
        return body;
    }
}
