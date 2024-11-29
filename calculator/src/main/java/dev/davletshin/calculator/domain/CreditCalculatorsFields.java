package dev.davletshin.calculator.domain;

import dev.davletshin.calculator.web.dto.credit.PaymentScheduleElementDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CreditCalculatorsFields {
    private BigDecimal psk;
    private BigDecimal monthlyPayment;
    private List<PaymentScheduleElementDto> paymentSchedule;
}
