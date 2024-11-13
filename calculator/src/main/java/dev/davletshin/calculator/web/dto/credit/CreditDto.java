package dev.davletshin.calculator.web.dto.credit;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
//TODO take out comments
@Data
@Builder
public class CreditDto {
    private BigDecimal amount; //
    private int term; //
    private BigDecimal monthlyPayment;
    private BigDecimal rate;//
    private BigDecimal psk;
    private boolean isInsuranceEnabled;//
    private boolean isSalaryClient;//
    private List<PaymentScheduleElementDto> paymentSchedule;

    public void calculateCredit() {

    }
}
