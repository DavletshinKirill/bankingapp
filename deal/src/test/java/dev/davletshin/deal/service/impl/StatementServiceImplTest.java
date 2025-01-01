package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.exception.ResourceNotFoundException;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.interfaces.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {
    @MockBean
    private StatementRepository statementRepository;

    @Autowired
    private StatementService statementService;

    @Test
    void getStatement() {
        UUID id = UUID.randomUUID();
        Statement statement = Statement.builder()
                .id(id)
                .build();
        when(statementRepository.findById(id)).thenReturn(Optional.of(statement));
        Statement testStatement = statementService.getStatement(id);
        verify(statementRepository).findById(id);
        assertEquals(statement, testStatement);
    }

    @Test
    void getStatementThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(statementRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> statementService.getStatement(id));
        verify(statementRepository).findById(id);
    }

    @Test
    void saveStatement() {
        Statement statement = Statement.builder()
                .id(UUID.randomUUID())
                .build();
        when(statementRepository.save(statement)).thenReturn(statement);
        Statement testStatement = statementService.saveStatement(statement);
        verify(statementRepository).save(statement);
        assertEquals(statement, testStatement);
    }
}