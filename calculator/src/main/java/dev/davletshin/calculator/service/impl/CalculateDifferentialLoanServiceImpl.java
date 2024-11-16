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
    @Override
    public CreditCalculatorsFields calculateCredit(int term, BigDecimal rate, BigDecimal amount, boolean countMonthlyPayment) {

        BigDecimal monthlyRate = rate.divide(new BigDecimal("1200"), 10, RoundingMode.HALF_UP);
        BigDecimal psk = BigDecimal.ZERO;
        List<PaymentScheduleElementDto> paymentSchedule = countMonthlyPayment ? new ArrayList<>(term) : null;
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
            if (countMonthlyPayment) {
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
