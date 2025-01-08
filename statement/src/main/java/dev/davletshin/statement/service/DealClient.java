package dev.davletshin.statement.service;

import dev.davletshin.statement.web.dto.LoanOfferDto;
import dev.davletshin.statement.web.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealClient {
    List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto);

    void updateOffer(LoanOfferDto loanOfferDto);
}
