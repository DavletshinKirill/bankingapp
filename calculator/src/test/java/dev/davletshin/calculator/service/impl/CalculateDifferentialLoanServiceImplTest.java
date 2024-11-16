package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.config.TestConfig;
import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class CalculateDifferentialLoanServiceImplTest {

    @Autowired
    private CalculateDifferentialLoanService calculateDifferentialLoanService;

    @Test
    void calculateCredit() {
        int term = 3;
        BigDecimal rate = new BigDecimal("10");
        BigDecimal amount = new BigDecimal("40000");
        boolean countMonthlyPayment = false;
        CreditCalculatorsFields creditCalculatorsFields = calculateDifferentialLoanService.calculateCredit(term, rate, amount, countMonthlyPayment);

        // take result variables from online calculator https://calculator-credit.ru
        BigDecimal pskResult = new BigDecimal("40667");
        // sum all monthly payment and divide it into terms
        BigDecimal monthlyPaymentResult = new BigDecimal("13556");

        assertEquals(creditCalculatorsFields.getPsk(), pskResult);
        assertEquals(creditCalculatorsFields.getMonthlyPayment(), monthlyPaymentResult);
        assertNull(creditCalculatorsFields.getPaymentSchedule());
    }

    @Test
    void calculateCreditWithResults() {
        int term = 3;
        BigDecimal rate = new BigDecimal("10");
        BigDecimal amount = new BigDecimal("40000");
        boolean countMonthlyPayment = true;
        CreditCalculatorsFields creditCalculatorsFields = calculateDifferentialLoanService.calculateCredit(term, rate, amount, countMonthlyPayment);

        //take result variables from online calculator https://calculator-credit.ru
        BigDecimal pskResult = new BigDecimal("40667");
        // sum all monthly payment and divide it into terms
        BigDecimal monthlyPaymentResult = new BigDecimal("13556");

        assertEquals(creditCalculatorsFields.getPsk(), pskResult);
        assertEquals(creditCalculatorsFields.getMonthlyPayment(), monthlyPaymentResult);
        assertNotNull(creditCalculatorsFields.getPaymentSchedule());
        assertEquals(creditCalculatorsFields.getPaymentSchedule().size(), term);
    }
}