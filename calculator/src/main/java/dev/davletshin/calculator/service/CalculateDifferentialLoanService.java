package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;

import java.math.BigDecimal;

public interface CalculateDifferentialLoanService {
    CreditCalculatorsFields calculateCredit(int term, BigDecimal rate, BigDecimal amount, boolean countMonthlyPayment);
}
