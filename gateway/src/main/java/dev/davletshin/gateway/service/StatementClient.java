package dev.davletshin.gateway.service;


import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "statenentClient", url = "${http.urls.statement}")
public interface StatementClient {

    @PostMapping("${http.endpoints.statement.statement}")
    List<LoanOfferDto> createOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("${http.endpoints.statement.statementOffer}")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
