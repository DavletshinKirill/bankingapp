package dev.davletshin.calculator.service;

import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculateCreditImpl implements CalculateCredit {

    private final int defaultRate = 10;

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
                .build();
    }

    @Override
    public List<LoanOfferDto> generateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return List.of();
    }
}
