package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.exception.SesCodeNotConfirmed;
import dev.davletshin.deal.domain.statement.ApplicationStatus;
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
            saveSignStatement(statement);
            EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(statement.getClient(), statementId,
                    Theme.CREDIT_ISSUED, null);
            brokerSender.send(emailMessageDTO, Theme.CREDIT_ISSUED);
        } else throw new SesCodeNotConfirmed("Ses Code is not matched");
    }

    private void saveSignStatement(Statement statement) {
        if (statement.getSignDate() == null) {
            statement.setSignDate(LocalDateTime.now());
            statement.setStatus(ApplicationStatus.CREDIT_ISSUED);
            statementService.saveStatement(statement);
        }
    }

    private UUID saveSesCode(Statement statement) {
        UUID sesCode = UUID.randomUUID();
        statement.setSesCode(sesCode);
        statementService.saveStatement(statement);
        return sesCode;
    }
}
