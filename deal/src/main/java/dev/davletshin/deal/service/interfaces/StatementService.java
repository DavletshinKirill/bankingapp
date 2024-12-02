package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;

public interface StatementService {
    Statement createStatement(Client client);
}
