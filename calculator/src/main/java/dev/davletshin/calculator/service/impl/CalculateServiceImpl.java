package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.domain.OffersCreation;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    private final CalculateDifferentialLoanService calculateLoanService;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        scoringDataDto.checkAmountSalary();
        scoringDataDto.checkAge();

        BigDecimal rate = new BigDecimal(
                defaultRate + scoringDataDto.indexGender()
                        + scoringDataDto.getMaritalStatus().getIndexMaritalStatus() + scoringDataDto.checkEmployment()
        );

        CreditCalculatorsFields creditCalculatorsFields = calculateLoanService.calculateCredit(
                scoringDataDto.getTerm(), rate, scoringDataDto.getAmount(),
                true
        );
        return new CreditDto(
                scoringDataDto.getAmount(),
                scoringDataDto.getTerm(),
                creditCalculatorsFields.getMonthlyPayment(),
                rate,
                creditCalculatorsFields.getPsk(),
                scoringDataDto.getIsInsuranceEnabled(),
                scoringDataDto.getIsSalaryClient(),
                creditCalculatorsFields.getPaymentSchedule()
        );
    }

    @Override
    public List<LoanOfferDto> generateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = new ArrayList<>();

        for (OffersCreation offerCreation : OffersCreation.values()) {
            offers.add(createLoanOfferDto(offerCreation, loanStatementRequestDto));
        }
        return offers.stream().sorted(Comparator.comparing(LoanOfferDto::getRate))
                .toList();
    }

    private LoanOfferDto createLoanOfferDto(OffersCreation offersCreation,
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
