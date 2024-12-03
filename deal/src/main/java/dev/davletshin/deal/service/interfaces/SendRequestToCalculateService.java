package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.web.dto.CreditDto;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import dev.davletshin.deal.web.dto.ScoringDataDto;

import java.util.List;

public interface SendRequestToCalculateService {
    List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto);

    CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto);
}
