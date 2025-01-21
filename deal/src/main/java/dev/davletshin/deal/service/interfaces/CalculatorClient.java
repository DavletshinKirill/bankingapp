package dev.davletshin.deal.service.interfaces;


import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorClient {
    List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto);
}
