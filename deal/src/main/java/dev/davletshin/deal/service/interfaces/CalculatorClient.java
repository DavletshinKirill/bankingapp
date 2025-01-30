package dev.davletshin.deal.service.interfaces;


import dev.davletshin.deal.web.dto.CreditDto;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import dev.davletshin.deal.web.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "calculatorClient", url = "${http.base.url}")
public interface CalculatorClient {

    @PostMapping("${http.endpoints.offers}")
    List<LoanOfferDto> postRequestToCalculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("${http.endpoints.calculator}")
    CreditDto postRequestToCalculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
