package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;

import java.util.UUID;

public interface StatementService {
    Statement createAndSaveNewStatement(Client client);

    Statement getStatement(UUID statementId);

    Statement saveStatement(Statement statement);
}
