package dev.davletshin.deal.domain.credit;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentSchedule {
    private int number;

    private LocalDate date;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "interes_payment")
    private BigDecimal interestPayment;

    @Column(name = "debt_payment")
    private BigDecimal debtPayment;

    @Column(name = "remaining_debt")
    private BigDecimal remainingDebt;
}
