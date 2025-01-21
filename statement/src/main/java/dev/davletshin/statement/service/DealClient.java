package dev.davletshin.statement.service;


import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;

import java.util.List;

public interface DealClient {
    List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto);

    void updateOffer(LoanOfferDto loanOfferDto);
}
