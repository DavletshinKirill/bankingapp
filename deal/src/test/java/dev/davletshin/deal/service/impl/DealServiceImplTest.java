package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.client.Employment;
import dev.davletshin.deal.domain.client.Passport;
import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.enums.Theme;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.factory.EmailMessageFactory;
import dev.davletshin.deal.service.factory.PassportFactory;
import dev.davletshin.deal.service.factory.ScoringDataFactory;
import dev.davletshin.deal.service.factory.StatusHistoryFactory;
import dev.davletshin.deal.service.interfaces.*;
import dev.davletshin.deal.web.dto.*;
import dev.davletshin.deal.web.mapper.CreditMapper;
import dev.davletshin.deal.web.mapper.EmploymentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DealServiceImplTest {

    @Mock
    private ClientService clientService;

    @Mock
    private StatementService statementService;

    @Mock
    private CalculatorClient sendRequestToCalculateService;

    @Mock
    private PassportFactory passportFactory;

    @Mock
    private StatusHistoryFactory statusHistoryFactory;

    @Mock
    private EmploymentMapper employmentMapper;

    @Mock
    private CreditMapper creditMapper;

    @Mock
    private CreditService creditService;

    @Mock
    private ScoringDataFactory scoringDataFactory;

    @Mock
    private EmailMessageFactory emailMessageFactory;

    @Mock
    private BrokerSender brokerSender;

    @InjectMocks
    private DealServiceImpl dealService;

    private LoanStatementRequestDto loanStatementRequestDto;
    private Client client;
    private Passport passport;
    private Statement statement;
    private LoanOfferDto loanOfferDto;
    private Credit credit;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;
    private ScoringDataDto scoringDataDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setPassportSeries("1234");
        loanStatementRequestDto.setPassportNumber("567890");

        client = new Client();
        passport = Passport.builder()
                .issueBranch("Some Branch")
                .issueDate(LocalDate.of(2023, 10, 5))
                .build();
        client.setPassport(passport);
        statement = new Statement();
        statement.setId(UUID.randomUUID());
        statement.setAppliedOffer(new LoanOfferDto());
        statement.setStatusHistory(new ArrayList<>());
        statement.setClient(client);
        loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(UUID.randomUUID());

        finishRegistrationRequestDto = new FinishRegistrationRequestDto();
        scoringDataDto = new ScoringDataDto();
        credit = new Credit();
    }

    @Test
    void createClientAndStatement() {
        when(passportFactory.createNewPassportWithNumberAndSeries("1234", "567890")).thenReturn(passport);
        client.setPassport(passport);
        when(clientService.createClient(client)).thenReturn(client);
        when(statementService.saveStatement(statement)).thenReturn(statement);

        LoanOfferDto loanOfferDto = new LoanOfferDto();
        when(sendRequestToCalculateService.postRequestToCalculateOffers(loanStatementRequestDto))
                .thenReturn(Collections.singletonList(loanOfferDto));

        List<LoanOfferDto> result = dealService.createClientAndStatement(loanStatementRequestDto, client);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(passportFactory).createNewPassportWithNumberAndSeries("1234", "567890");
        verify(clientService).createClient(client);
        verify(sendRequestToCalculateService).postRequestToCalculateOffers(loanStatementRequestDto);
    }

    @Test
    void updateStatement() {
        when(statementService.getStatement(loanOfferDto.getStatementId())).thenReturn(statement);

        dealService.updateStatement(loanOfferDto);

        assertEquals(ApplicationStatus.PREAPPROVAL, statement.getStatus());

        assertEquals(loanOfferDto, statement.getAppliedOffer());
        assertEquals(1, statement.getStatusHistory().size());
        verify(statusHistoryFactory).createStatusHistory(ApplicationStatus.PREAPPROVAL);
        verify(statementService).saveStatement(statement);
    }

    @Test
    void calculateCredit() {
        UUID statementUUID = UUID.randomUUID();
        when(statementService.getStatement(statementUUID)).thenReturn(statement);
        when(scoringDataFactory.createScoringData(statement, client, finishRegistrationRequestDto)).thenReturn(scoringDataDto);
        when(passportFactory.fillIssueBranchAndDate(any(), any(), any())).thenReturn(client.getPassport());
        Employment employment = new Employment();
        when(employmentMapper.toEntity(finishRegistrationRequestDto.getEmployment())).thenReturn(employment);
        when(sendRequestToCalculateService.postRequestToCalculateCredit(scoringDataDto)).thenReturn(CreditDto.builder().build());
        when(creditMapper.toEntity(any())).thenReturn(credit);
        when(creditService.createCredit(credit)).thenReturn(credit);
        when(clientService.createClient(any())).thenReturn(client);
        when(emailMessageFactory.createEmailMessage(client, statementUUID, Theme.CREATE_DOCUMENTS, null)).thenReturn(new EmailMessageDTO());
        dealService.calculateCredit(statementUUID, finishRegistrationRequestDto);

        assertEquals(credit, statement.getCredit());
        assertEquals(client, statement.getClient());
        verify(statementService).saveStatement(statement);
        verify(creditService).createCredit(credit);
        verify(clientService).createClient(any());
    }
}