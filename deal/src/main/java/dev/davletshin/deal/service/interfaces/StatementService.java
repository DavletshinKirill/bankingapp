package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.web.dto.StatementDto;

import java.util.List;
import java.util.UUID;

public interface StatementService {

    Statement getStatement(UUID statementId);
    StatementDto getStatementById(UUID statementId);

    Statement saveStatement(Statement statement);

    List<StatementDto> getStatements();

    void updateStatementStatus(UUID statementId, ApplicationStatus status);
}
