package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.exception.SesCodeNotConfirmed;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.factory.EmailMessageFactory;
import dev.davletshin.deal.service.interfaces.BrokerSender;
import dev.davletshin.deal.service.interfaces.DossierService;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.shared.dto.EmailMessageDTO;
import dev.davletshin.shared.dto.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DossierServiceImpl implements DossierService {

    private final StatementService statementService;
    private final EmailMessageFactory emailMessageFactory;
    private final BrokerSender brokerSender;

    @Override
    public void requestSendDocumentEmail(UUID statementId) {
        Statement statement = statementService.getStatement(statementId);
        Client client = statement.getClient();
        EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(client, statementId, Theme.SEND_DOCUMENTS, null);
        brokerSender.send(emailMessageDTO, Theme.SEND_DOCUMENTS);
    }

    @Override
    public void requestSignDocument(UUID statementId) {
        Statement statement = statementService.getStatement(statementId);
        Client client = statement.getClient();
        UUID sesCode = UUID.randomUUID();
        statement.setSesCode(sesCode);
        statementService.saveStatement(statement);
        EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(client, statementId, Theme.SEND_SES, sesCode);
        brokerSender.send(emailMessageDTO, Theme.SEND_SES);
    }

    @Override
    public void signCodeDocument(UUID statementId, UUID sesCode) {
        Statement statement = statementService.getStatement(statementId);
        Client client = statement.getClient();
        if (statement.getSesCode().equals(sesCode)) {
            statement.setSignDate(LocalDateTime.now());
            statementService.saveStatement(statement);
            EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(client, statementId, Theme.CREDIT_ISSUED, null);
            brokerSender.send(emailMessageDTO, Theme.CREDIT_ISSUED);
        } else throw new SesCodeNotConfirmed("Ses Code is not matched");
    }
}
