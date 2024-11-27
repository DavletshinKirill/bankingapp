package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.domain.OffersCreation;
import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.service.ScoringService;
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
    private final ScoringService scoringService;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        scoringService.checkAmountSalary(scoringDataDto);
        scoringService.checkAge(scoringDataDto);

        BigDecimal rate = new BigDecimal(
                defaultRate + scoringService.getIndexGender(scoringDataDto)
                        + scoringDataDto.getMaritalStatus().getIndexMaritalStatus() + scoringService.getIndexEmployment(scoringDataDto)
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

        new LoanOfferDto();
        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(loanStatementRequestDto.getAmount())
                .totalAmount(creditCalculatorsFields.getPsk())
                .term(loanStatementRequestDto.getTerm())
                .monthlyPayment(creditCalculatorsFields.getMonthlyPayment())
                .rate(rate)
                .isInsuranceEnabled(offersCreation.isInsuranceEnabled())
                .isSalaryClient(offersCreation.isSalaryClientEnabled())
                .build();
    }
}
