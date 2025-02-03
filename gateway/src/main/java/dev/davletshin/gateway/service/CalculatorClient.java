package dev.davletshin.gateway.service;



import dev.davletshin.gateway.web.dto.CreditDto;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import dev.davletshin.gateway.web.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "calculatorClient", url = "${http.urls.calculator}")
public interface CalculatorClient {
    @PostMapping("${http.endpoints.calculator.calculatorOffers}")
    List<LoanOfferDto> postRequestToCalculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("${http.endpoints.calculator.calculatorCalc}")
    CreditDto postRequestToCalculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
