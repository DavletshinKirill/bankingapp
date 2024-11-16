package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.config.TestConfig;
import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// take result variables from online calculator https://calculator-credit.ru
// to calculate monthlyPayment sum all monthly payment and divide it into terms
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
class CalculateServiceImplTest {

    @Autowired
    private CalculateService calculateService;

    @Test
    void calculateCredit() {
        ScoringDataDto scopingDataDto = new ScoringDataDto(
                Gender.MALE,
                LocalDate.of(2020, 1, 1),
                "г. Воронеж",
                3,
                "someString",
                true,
                true,
                MaritalStatus.UNMARRIED,
                new EmploymentDto(
                        EmploymentStatus.UNEMPLOYED,
                        "123456789012",
                        BigDecimal.valueOf(50000),
                        Position.MIDDLE_MANAGER,
                        20,
                        6
                ),
                new BigDecimal("40000"),
                6,
                "Иван",
                "Иванов",
                "Иванович",
                "example@example.com",
                LocalDate.of(1990, 1, 1),
                "2020",
                "123456"
        );
        BigDecimal pskResult = new BigDecimal("41283");
        BigDecimal monthlyPaymentResult = new BigDecimal("6881");
        CreditDto creditDto = calculateService.calculateCredit(scopingDataDto);


        assertEquals(creditDto.getPsk(), pskResult);
        assertEquals(creditDto.getMonthlyPayment(), monthlyPaymentResult);
        assertNotNull(creditDto.getPaymentSchedule());
        assertEquals(creditDto.getPaymentSchedule().size(), scopingDataDto.getTerm());

    }

    @Test
    void generateOffers() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto(
                new BigDecimal("40000"),
                6,
                "Иван",
                "Иванов",
                "Иванович",
                "example@example.com",
                LocalDate.of(1990, 1, 1),
                "2020",
                "123456"
        );
        boolean isSorted = true;
        List<LoanOfferDto> loanOfferDtoList = calculateService.generateOffers(loanStatementRequestDto);

        for (int i = 0; i < loanOfferDtoList.size() - 1; i++) {
            if (loanOfferDtoList.get(i).getRate().compareTo(loanOfferDtoList.get(i + 1).getRate()) > -1) {
                isSorted = false;
                break;
            }
        }
        assertEquals(loanOfferDtoList.size(), 4);
        assertTrue(isSorted, "The list is not sorted by rate");
    }
}