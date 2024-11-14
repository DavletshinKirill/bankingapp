package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.domain.OffersCreation;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OffersFactory {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    private final CalculateDifferentialLoanService calculateLoanService;

    public LoanOfferDto createLoanOfferDto(OffersCreation offersCreation,
                                           LoanStatementRequestDto loanStatementRequestDto) {
        BigDecimal rate = new BigDecimal(defaultRate + offersCreation.getInsuranceRate() + offersCreation.getSalaryClient());
        CreditCalculatorsFields creditCalculatorsFields = calculateLoanService.calculateCredit(
                loanStatementRequestDto.getTerm(), rate, loanStatementRequestDto.getAmount(),
                false
        );

        return new LoanOfferDto(
                UUID.randomUUID(),
                loanStatementRequestDto.getAmount(),
                creditCalculatorsFields.getPsk(),
                loanStatementRequestDto.getTerm(),
                creditCalculatorsFields.getMonthlyPayment(),
                rate,
                offersCreation.isInsuranceEnabled(),
                offersCreation.isSalaryClientEnabled()
        );

    }

}
