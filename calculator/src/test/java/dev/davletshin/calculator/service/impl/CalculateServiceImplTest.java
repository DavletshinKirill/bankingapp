package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.config.TestConfig;
import dev.davletshin.calculator.domain.*;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.service.ScoringService;
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
    private LoanStatementRequestDto loanStatementRequestDto;

    @Mock
    private EmploymentDto employment;

    @Autowired
    private ScoringService scoringService;

    @Autowired
    private CalculateService calculateService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(scoringDataDto.getTerm()).thenReturn(12);
    }


    @Test
    void testCalculateCredit() {
        BigDecimal amount = new BigDecimal("42124");
        CreditCalculatorsFields creditCalculatorsFields = new CreditCalculatorsFields(
                amount,
                amount.divide(BigDecimal.valueOf(scoringDataDto.getTerm()), 2, RoundingMode.HALF_UP),
                new ArrayList<>(scoringDataDto.getTerm())
        );
        when(scoringDataDto.getEmployment()).thenReturn(employment);
        when(employment.getSalary()).thenReturn(new BigDecimal("10000"));
        when(scoringDataDto.getAmount()).thenReturn(new BigDecimal("40000"));
        when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(1990, 1, 1));
        when(scoringDataDto.getGender()).thenReturn(Gender.MALE);
        when(scoringDataDto.getMaritalStatus()).thenReturn(MaritalStatus.MARRIED);
        when(employment.getPosition()).thenReturn(Position.MIDDLE_MANAGER);
        when(employment.getEmploymentStatus()).thenReturn(EmploymentStatus.BUSINESS_OWNER);
        when(employment.getWorkExperienceTotal()).thenReturn(20);
        when(employment.getWorkExperienceCurrent()).thenReturn(5);

        assertDoesNotThrow(() -> scoringService.checkAmountSalary(scoringDataDto));
        assertDoesNotThrow(() -> scoringService.checkAge(scoringDataDto));

        BigDecimal rate = new BigDecimal(
                defaultRate + scoringService.getIndexGender(scoringDataDto)
                        + scoringDataDto.getMaritalStatus().getIndexMaritalStatus() + scoringService.getIndexEmployment(scoringDataDto)
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
        when(loanStatementRequestDto.getTerm()).thenReturn(12);
        when(loanStatementRequestDto.getAmount()).thenReturn(new BigDecimal("40000"));

        BigDecimal amount = new BigDecimal("42124");
        CreditCalculatorsFields creditCalculatorsFields = new CreditCalculatorsFields(
                amount,
                amount.divide(BigDecimal.valueOf(scoringDataDto.getTerm()), 2, RoundingMode.HALF_UP),
                new ArrayList<>(scoringDataDto.getTerm())
        );

        when(calculateDifferentialLoanService.calculateCredit(anyInt(), any(BigDecimal.class), any(BigDecimal.class), anyBoolean()))
                .thenReturn(creditCalculatorsFields);

        List<LoanOfferDto> offers = calculateService.generateOffers(loanStatementRequestDto);

        assertNotNull(offers);
        assertEquals(offers.size(), 4);

    }
}