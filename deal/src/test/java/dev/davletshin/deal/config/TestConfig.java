package dev.davletshin.deal.config;

import dev.davletshin.deal.repository.ClientRepository;
import dev.davletshin.deal.repository.CreditRepository;
import dev.davletshin.deal.repository.StatementRepository;
import dev.davletshin.deal.service.factory.PassportFactory;
import dev.davletshin.deal.service.factory.ScoringDataFactory;
import dev.davletshin.deal.service.factory.StatusHistoryFactory;
import dev.davletshin.deal.service.impl.ClientServiceImpl;
import dev.davletshin.deal.service.impl.CreditServiceImpl;
import dev.davletshin.deal.service.impl.StatementServiceImpl;
import dev.davletshin.deal.service.interfaces.ClientService;
import dev.davletshin.deal.service.interfaces.CreditService;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.mapper.ClientToLoanStatementRequestMapper;
import dev.davletshin.deal.web.mapper.CreditMapper;
import dev.davletshin.deal.web.mapper.EmploymentMapper;
import dev.davletshin.deal.web.mapper.FinishRegistrationRequestToScoringDataMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public PassportFactory passportFactory() {
        return new PassportFactory();
    }

    @Bean
    @Primary
    public StatusHistoryFactory statusHistoryFactory() {
        return new StatusHistoryFactory();
    }

    @Bean
    @Primary
    public ClientRepository clientRepository() {
        return Mockito.mock(ClientRepository.class);
    }

    @Bean
    @Primary
    public ClientService clientService() {
        return new ClientServiceImpl(clientRepository());
    }

    @Bean
    @Primary
    public CreditRepository creditRepository() {
        return Mockito.mock(CreditRepository.class);
    }

    @Bean
    @Primary
    public CreditService creditService() {
        return new CreditServiceImpl(creditRepository());
    }

    @Bean
    @Primary
    public StatementRepository statementRepository() {
        return Mockito.mock(StatementRepository.class);
    }

    @Bean
    @Primary
    public StatementService statementService() {
        return new StatementServiceImpl(statementRepository());
    }

    @Bean
    @Primary
    public FinishRegistrationRequestToScoringDataMapper finishRegistrationRequestToScoringDataMapper() {
        return Mockito.mock(FinishRegistrationRequestToScoringDataMapper.class);
    }

    @Bean
    @Primary
    public ClientToLoanStatementRequestMapper clientToLoanStatementRequestMapper() {
        return Mockito.mock(ClientToLoanStatementRequestMapper.class);
    }

    @Bean
    @Primary
    public EmploymentMapper employmentMapper() {
        return Mockito.mock(EmploymentMapper.class);
    }

    @Bean
    @Primary
    public CreditMapper creditMapper() {
        return Mockito.mock(CreditMapper.class);
    }

    @Bean
    @Primary
    public ScoringDataFactory scoringDataFactory() {
        return new ScoringDataFactory(finishRegistrationRequestToScoringDataMapper(), clientToLoanStatementRequestMapper());
    }
}
