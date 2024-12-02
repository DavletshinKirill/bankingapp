package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.interfaces.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementRepository statementRepository;


    @Override
    public Statement createStatement(Client client) {
        Statement statement = new Statement();
        statement.setClient(client);
        return statementRepository.save(statement);
    }
}
