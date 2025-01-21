package dev.davletshin.deal.service.factory;

import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.mapper.ClientToLoanStatementRequestMapper;
import dev.davletshin.deal.web.mapper.FinishRegistrationRequestToScoringDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
class ScoringDataFactoryTest {

    @MockBean
    private FinishRegistrationRequestToScoringDataMapper finishRegistrationRequestToScoringDataMapper;

    @MockBean
    private ClientToLoanStatementRequestMapper clientToLoanStatementRequestMapper;

    @Autowired
    private ScoringDataFactory scoringDataFactory;

    private Statement statement;
    private Client client;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        statement = new Statement();
        client = new Client();
        finishRegistrationRequestDto = new FinishRegistrationRequestDto();
    }

    @Test
    public void testCreateScoringData() {

        LoanOfferDto appliedOffer = new LoanOfferDto();
        appliedOffer.setRequestedAmount(BigDecimal.valueOf(10000));
        appliedOffer.setTerm(12);
        appliedOffer.setSalaryClient(true);
        appliedOffer.setInsuranceEnabled(false);

        statement.setAppliedOffer(appliedOffer);

        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        when(clientToLoanStatementRequestMapper.toLoanStatementRequestDto(client, BigDecimal.valueOf(10000), 12))
                .thenReturn(loanStatementRequestDto);

        ScoringDataDto scoringDataDto = new ScoringDataDto();
        when(finishRegistrationRequestToScoringDataMapper.toScoringDataDto(finishRegistrationRequestDto, false, true))
                .thenReturn(scoringDataDto);

        ScoringDataDto result = scoringDataFactory.createScoringData(statement, client, finishRegistrationRequestDto);

        assertNotNull(result);
        verify(clientToLoanStatementRequestMapper).toLoanStatementRequestDto(client, BigDecimal.valueOf(10000), 12);
        verify(finishRegistrationRequestToScoringDataMapper).toScoringDataDto(finishRegistrationRequestDto, false, true);
    }

}