package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.exception.ResourceNotFoundException;
import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.StatementDto;
import dev.davletshin.deal.web.mapper.StatementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;
    private final StatementMapper statementMapper;

    @Override
    public Statement getStatement(UUID statementId) {
        return statementRepository.findById(statementId)
                .orElseThrow(() -> new ResourceNotFoundException("Statement by id not found exception"));
    }

    @Override
    @Transactional(readOnly = true)
    public StatementDto getStatementById(UUID statementId) {
        Statement statement = getStatement(statementId);
        return statementMapper.toDTO(statement);
    }

    @Override
    public Statement saveStatement(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatementDto> getStatements() {
        return statementMapper.toDTO(statementRepository.findAll());
    }

    @Transactional
    @Override
    public void updateStatementStatus(UUID statementId, ApplicationStatus status) {
        Statement statement = getStatement(statementId);
        statement.setStatus(status);
        if (status == ApplicationStatus.CREDIT_ISSUED && statement.getSignDate() == null) {
            statement.setSignDate(LocalDateTime.now());
        }
        statementRepository.save(statement);
    }
}
