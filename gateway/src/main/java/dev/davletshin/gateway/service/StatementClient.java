package dev.davletshin.gateway.service;


import dev.davletshin.gateway.web.dto.LoanOfferDto;

public interface StatementClient {

    void updateOffer(LoanOfferDto loanOfferDto, String baseUrl, String finalUrl);
}
