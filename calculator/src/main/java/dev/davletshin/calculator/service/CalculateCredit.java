package dev.davletshin.calculator.service;

import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;

import java.util.List;

public interface CalculateCredit {
    CreditDto calculateCredit(ScoringDataDto scoringDataDto);

    List<LoanOfferDto> generateOffers(LoanStatementRequestDto loanStatementRequestDto);
}
