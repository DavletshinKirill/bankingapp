package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.shared.dto.EmailMessageDTO;
import dev.davletshin.shared.dto.Theme;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailMessageFactory {
    public EmailMessageDTO createEmailMessage(Client client, UUID statementUUID, Theme theme, UUID ses_code) {
        String text = getEmailTExt(theme, client, ses_code);
        return new EmailMessageDTO(client.getEmail(), statementUUID, text, theme);
    }

    private String getEmailTExt(Theme theme, Client client, UUID ses_code) {
        String body;
        switch (theme) {
            case FINISH_REGISTRATION ->
                    body = "Congratulations on your successful registration on bankingapp! We are glad to welcome you to our community and we are sure that your participation will bring you a lot of useful and interesting things.";
            case SEND_DOCUMENTS -> body = "The request to send documents is being processed";
            case SEND_SES -> {
                if (ses_code != null) {
                    body = String.format("""
                            //                            The document signing request is being processed
                                                        Confirmation Code - %s
                            """, ses_code);
                } else throw new NullPointerException("ses_code is null");
            }
            case CREDIT_ISSUED -> body = "The documents are signed";
            default -> throw new IllegalStateException("Unexpected value: " + theme);
        }
        return String.format(
                """
                        Dear %s,
                        
                        %s
                        """, client.getFirstName() + " " + client.getLastName(), body
        );
    }
}
