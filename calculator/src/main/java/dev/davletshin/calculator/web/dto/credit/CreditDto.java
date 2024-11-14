package dev.davletshin.calculator.web.dto.credit;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class CreditDto {
    private BigDecimal amount;
    private int term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
    private List<PaymentScheduleElementDto> paymentSchedule;

    public CreditDto calculateCredit() {
        BigDecimal monthlyRate = rate.divide(new BigDecimal("1200"), 10, RoundingMode.HALF_UP);

        BigDecimal psk = BigDecimal.ZERO;
        paymentSchedule = new ArrayList<>(term);
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
            paymentSchedule.add(new PaymentScheduleElementDto(
                    month, currentDate.plusMonths(month), totalPayment, interestPayment,
                    monthlyPayment, remainingPrincipal
            ));
        }
        psk = psk.setScale(2, RoundingMode.HALF_UP);
        monthlyPayment = psk.divide(new BigDecimal(term), 10, RoundingMode.HALF_UP);
        return this;
    }
}
