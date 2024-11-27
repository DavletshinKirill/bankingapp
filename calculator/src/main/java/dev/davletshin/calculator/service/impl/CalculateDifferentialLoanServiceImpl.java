package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.web.dto.credit.PaymentScheduleElementDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateDifferentialLoanServiceImpl implements CalculateDifferentialLoanService {
    private final static int MONTHS_IN_YEAR = 12;
    private final static int AMOUNT_PERCENTS = 100;

    @Override
    public CreditCalculatorsFields calculateCredit(int term, BigDecimal rate, BigDecimal amount, boolean isCountMonthlyPayment) {

        BigDecimal monthlyRate = rate.divide(new BigDecimal(MONTHS_IN_YEAR * AMOUNT_PERCENTS), 10, RoundingMode.HALF_UP);
        BigDecimal psk = BigDecimal.ZERO;
        List<PaymentScheduleElementDto> paymentSchedule = isCountMonthlyPayment ? new ArrayList<>(term) : null;
        LocalDate currentDate = LocalDate.now();

        for (int month = 1; month <= term; month++) {
            BigDecimal totalPayment = amount.divide(
                    new BigDecimal(term), 10, RoundingMode.HALF_UP);

            BigDecimal remainingPrincipalToCalculateInterestPayment = amount.subtract(
                    totalPayment.multiply(new BigDecimal(month - 1)));
            BigDecimal interestPayment = remainingPrincipalToCalculateInterestPayment.multiply(monthlyRate);

            BigDecimal monthlyPayment = totalPayment.add(interestPayment);
            BigDecimal remainingPrincipal = amount.subtract(
                    totalPayment.multiply(new BigDecimal(month)));

            if (remainingPrincipal.equals(new BigDecimal("2e-10"))) {
                remainingPrincipal = BigDecimal.ZERO;
            }
            psk = psk.add(monthlyPayment);
            if (isCountMonthlyPayment) {
                paymentSchedule.add(new PaymentScheduleElementDto(
                        month, currentDate.plusMonths(month), totalPayment.setScale(2, RoundingMode.HALF_UP), interestPayment.setScale(2, RoundingMode.HALF_UP),
                        monthlyPayment.setScale(2, RoundingMode.HALF_UP), remainingPrincipal.setScale(2, RoundingMode.HALF_UP)
                ));
            }
        }
        return new CreditCalculatorsFields(
                psk.setScale(0, RoundingMode.HALF_UP),
                psk.divide(new BigDecimal(term), 10, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP),
                paymentSchedule
        );
    }
}
