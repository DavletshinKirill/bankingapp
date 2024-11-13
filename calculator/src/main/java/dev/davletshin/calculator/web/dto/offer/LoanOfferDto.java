package dev.davletshin.calculator.web.dto.offer;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanOfferDto {
    private UUID statementId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private int term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private boolean isInsuranceEnabled;
    private boolean isSalaryClient;
}
