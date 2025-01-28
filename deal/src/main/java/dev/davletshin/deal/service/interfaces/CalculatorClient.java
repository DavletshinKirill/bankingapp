package dev.davletshin.deal.service.interfaces;


import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
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
