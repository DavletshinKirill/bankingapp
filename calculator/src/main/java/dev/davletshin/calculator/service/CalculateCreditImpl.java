package dev.davletshin.calculator.service;

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
public class CalculateCreditImpl implements CalculateCredit {

    @Value("${credit.info.defaultRate}")
    private int defaultRate;

    private final OffersFactory offersFactory;

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        scoringDataDto.checkAmountSalary();
        scoringDataDto.checkAge();
        int resultPercent = defaultRate + scoringDataDto.checkGender()
                + scoringDataDto.checkMaritalStatus() + scoringDataDto.checkEmployment();

        return CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .rate(BigDecimal.valueOf(resultPercent))
                .build().calculateCredit();
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
