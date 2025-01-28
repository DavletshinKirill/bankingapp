package dev.davletshin.deal.service.impl;

import dev.davletshin.calculator.web.dto.EmailMessageDTO;
import dev.davletshin.calculator.web.dto.Theme;
import dev.davletshin.deal.domain.exception.SesCodeNotConfirmed;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.factory.EmailMessageFactory;
import dev.davletshin.deal.service.interfaces.BrokerSender;
import dev.davletshin.deal.service.interfaces.DossierService;
import dev.davletshin.deal.service.interfaces.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(statement.getClient(), statementId,
                Theme.SEND_DOCUMENTS, null);
        brokerSender.send(emailMessageDTO, Theme.SEND_DOCUMENTS);
    }

    @Override
    public void requestSignDocument(UUID statementId) {
        Statement statement = statementService.getStatement(statementId);
        UUID sesCode = saveSesCode(statement);
        EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(statement.getClient(), statementId,
                Theme.SEND_SES, sesCode);
        brokerSender.send(emailMessageDTO, Theme.SEND_SES);
    }

    @Override
    public void signCodeDocument(UUID statementId, UUID sesCode) {
        Statement statement = statementService.getStatement(statementId);
        if (statement.getSesCode().equals(sesCode)) {
            EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(statement.getClient(), statementId,
                    Theme.CREDIT_ISSUED, null);
            brokerSender.send(emailMessageDTO, Theme.CREDIT_ISSUED);
        } else throw new SesCodeNotConfirmed("Ses Code is not matched");
    }

    private UUID saveSesCode(Statement statement) {
        UUID sesCode = UUID.randomUUID();
        statement.setSesCode(sesCode);
        statementService.saveStatement(statement);
        return sesCode;
    }
}
