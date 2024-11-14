package dev.davletshin.calculator.service;

import dev.davletshin.calculator.domain.CreditCalculatorsFields;
import dev.davletshin.calculator.domain.OffersCreation;
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

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    private final OffersFactory offersFactory;
    private final CalculateDifferentialLoanService calculateLoanService;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        scoringDataDto.checkAmountSalary();
        scoringDataDto.checkAge();

        BigDecimal rate = new BigDecimal(
                defaultRate + scoringDataDto.checkGender()
                        + scoringDataDto.checkMaritalStatus() + scoringDataDto.checkEmployment()
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
            offers.add(offersFactory.createLoanOfferDto(offerCreation, loanStatementRequestDto));
        }
        return offers.stream().sorted(Comparator.comparing(LoanOfferDto::getRate))
                .toList();
    }
}
