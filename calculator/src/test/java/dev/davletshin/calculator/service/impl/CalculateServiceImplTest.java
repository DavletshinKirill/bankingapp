package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.config.TestConfig;
import dev.davletshin.calculator.domain.*;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// take result variables from online calculator https://calculator-credit.ru
// to calculate monthlyPayment sum all monthly payment and divide it into terms
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
class CalculateServiceImplTest {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    @MockBean
    private CalculateDifferentialLoanService calculateDifferentialLoanService;

    @Mock
    private ScoringDataDto scoringDataDto;

    @Mock
    private OffersCreation offersCreation;

    @Autowired
    private CalculateService calculateService;

    //private final LogData logData = LogData.getInstance();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set default values for the mocked scoringDataDto
        when(scoringDataDto.getTerm()).thenReturn(12);
        lenient().when(scoringDataDto.getAmount()).thenReturn(new BigDecimal("40000"));
        lenient().when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(1990, 1, 1));
        lenient().when(scoringDataDto.getIsInsuranceEnabled()).thenReturn(true);
        lenient().when(scoringDataDto.getIsSalaryClient()).thenReturn(false);
        lenient().when(scoringDataDto.getGender()).thenReturn(Gender.MALE);
        lenient().when(scoringDataDto.getMaritalStatus()).thenReturn(MaritalStatus.MARRIED);
        lenient().when(scoringDataDto.getEmployment()).thenReturn(
                new EmploymentDto(
                        EmploymentStatus.BUSINESS_OWNER,
                        "123456789012",
                        new BigDecimal("10000"),
                        Position.MIDDLE_MANAGER,
                        20, 6
                ));
        lenient().when(offersCreation.getInsuranceRate()).thenReturn(1);
        lenient().when(offersCreation.getSalaryClient()).thenReturn(-1);

    }


    @Test
    void testCalculateCredit() {
        BigDecimal amount = new BigDecimal("42124");
        CreditCalculatorsFields creditCalculatorsFields = new CreditCalculatorsFields(
                amount,
                amount.divide(BigDecimal.valueOf(scoringDataDto.getTerm()), 2, RoundingMode.HALF_UP),
                new ArrayList<>(scoringDataDto.getTerm())
        );
        assertDoesNotThrow(scoringDataDto::checkAmountSalary);
        assertDoesNotThrow(scoringDataDto::checkAge);
        BigDecimal rate = new BigDecimal(
                defaultRate + scoringDataDto.indexGender()
                        + scoringDataDto.getMaritalStatus().getIndexMaritalStatus() + scoringDataDto.checkEmployment()
        );
        when(calculateDifferentialLoanService.calculateCredit(
                scoringDataDto.getTerm(), rate, scoringDataDto.getAmount(), true
        )).thenReturn(creditCalculatorsFields);
        CreditDto result = calculateService.calculateCredit(scoringDataDto);
        assertNotNull(result);
        assertEquals(result.getAmount(), scoringDataDto.getAmount());
        assertEquals(result.getRate(), rate);
        assertEquals(result.getPsk(), creditCalculatorsFields.getPsk());
        assertEquals(result.getTerm(), scoringDataDto.getTerm());
        assertEquals(result.getMonthlyPayment(), creditCalculatorsFields.getMonthlyPayment());
    }

    @Test
    public void generateOffers() {

        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("40000"));
        loanStatementRequestDto.setTerm(12);

        BigDecimal amount = new BigDecimal("42124");
        CreditCalculatorsFields creditCalculatorsFields = new CreditCalculatorsFields(
                amount,
                amount.divide(BigDecimal.valueOf(scoringDataDto.getTerm()), 2, RoundingMode.HALF_UP),
                new ArrayList<>(scoringDataDto.getTerm())
        );

        when(calculateDifferentialLoanService.calculateCredit(anyInt(), any(BigDecimal.class), any(BigDecimal.class), anyBoolean()))
                .thenReturn(creditCalculatorsFields);

        // Act
        List<LoanOfferDto> offers = calculateService.generateOffers(loanStatementRequestDto);

        // Assert
        assertNotNull(offers);
        assertEquals(offers.size(), 4);

    }
}