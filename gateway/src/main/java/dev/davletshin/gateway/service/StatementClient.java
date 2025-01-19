package dev.davletshin.gateway.service;

import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;

import java.util.List;

public interface StatementClient {
    List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto);

    void updateOffer(LoanOfferDto loanOfferDto);
}
