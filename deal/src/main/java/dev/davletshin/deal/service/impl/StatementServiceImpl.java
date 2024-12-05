package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.exception.ResourceNotFoundException;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.interfaces.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;

    @Transactional
    @Override
    public Statement createAndSaveNewStatement(Client client) {
        Statement statement = new Statement();
        statement.setClient(client);
        statement.setCreationDate(new Timestamp(System.currentTimeMillis()));
        return statementRepository.save(statement);
    }

    @Transactional(readOnly = true)
    @Override
    public Statement getStatement(UUID statementId) {
        return statementRepository.findById(statementId)
                .orElseThrow(() -> new ResourceNotFoundException("Statement by id not found exception"));
    }

    @Transactional()
    @Override
    public Statement saveStatement(Statement statement) {
        return statementRepository.save(statement);
    }
}
