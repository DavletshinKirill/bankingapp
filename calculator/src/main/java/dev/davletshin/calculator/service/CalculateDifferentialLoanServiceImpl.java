package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
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

            psk = psk.add(monthlyPayment);
            if (countMonthlyPayment) {
                paymentSchedule.add(new PaymentScheduleElementDto(
                        month, currentDate.plusMonths(month), totalPayment, interestPayment,
                        monthlyPayment, remainingPrincipal
                ));
            }
        }
        return new CreditCalculatorsFields(
                psk.setScale(2, RoundingMode.HALF_UP),
                psk.divide(new BigDecimal(term), 10, RoundingMode.HALF_UP),
                paymentSchedule
        );
    }
}
