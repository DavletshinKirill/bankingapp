package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.OffersCreation;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
public class OffersFactory {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    public LoanOfferDto createLoanOfferDto(OffersCreation offersCreation,
                                           LoanStatementRequestDto loanStatementRequestDto) {
        LoanOfferDto loanOfferDto = new LoanOfferDto();
        loanOfferDto.setStatementId(UUID.randomUUID());
        loanOfferDto.setTerm(loanStatementRequestDto.getTerm());
        loanOfferDto.setRequestedAmount(loanStatementRequestDto.getAmount());
        loanOfferDto.setRate(new BigDecimal(
                defaultRate + offersCreation.getInsuranceRate() + offersCreation.getSalaryClient())
        );
        loanOfferDto.setSalaryClient(offersCreation.isSalaryClientEnabled());
        loanOfferDto.setInsuranceEnabled(offersCreation.isInsuranceEnabled());
        calculateDifferentialPayment(loanOfferDto);
        return loanOfferDto;
    }

    private void calculateDifferentialPayment(LoanOfferDto loanOfferDto) {
        BigDecimal monthlyRate = loanOfferDto.getRate()
                .divide(new BigDecimal("12"), RoundingMode.HALF_UP);
        BigDecimal principalPayment = loanOfferDto.getRequestedAmount()
                .divide(new BigDecimal(loanOfferDto.getTerm()), RoundingMode.HALF_UP);
        BigDecimal totalPayment = BigDecimal.ZERO;

        for (int i = 1; i <= loanOfferDto.getTerm(); i++) {
            BigDecimal interestPayment = loanOfferDto.getRequestedAmount()
                    .subtract(principalPayment.multiply(new BigDecimal(i - 1))).multiply(monthlyRate);
            totalPayment = totalPayment.add(principalPayment.add(interestPayment));
        }
        loanOfferDto.setTotalAmount(totalPayment);
        BigDecimal monthlyPayment = totalPayment.divide(
                new BigDecimal(loanOfferDto.getTerm()), RoundingMode.HALF_UP
        );
        loanOfferDto.setMonthlyPayment(monthlyPayment);
    }
}
