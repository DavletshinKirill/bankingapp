package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.exception.ResourceNotFoundException;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.interfaces.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Override
    public Statement createAndSaveNewStatement(Client client) {
        Statement statement = new Statement();
        statement.setClient(client);
        return statementRepository.save(statement);
    }

    @Override
    public Statement getStatement(UUID statementId) {
        return statementRepository.findById(statementId)
                .orElseThrow(() -> new ResourceNotFoundException("Statement by id not found exception"));
    }

    @Override
    public Statement saveStatement(Statement statement) {
        return statementRepository.save(statement);
    }
}
