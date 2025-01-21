package dev.davletshin.gateway.service;

import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;

public interface StatementClient {

    void updateOffer(LoanOfferDto loanOfferDto, String baseUrl, String finalUrl);
}
