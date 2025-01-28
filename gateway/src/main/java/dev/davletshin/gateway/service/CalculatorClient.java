package dev.davletshin.gateway.service;



import dev.davletshin.gateway.web.dto.CreditDto;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import dev.davletshin.gateway.web.dto.ScoringDataDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "calculatorClient", url = "${http.base.url}")
public interface CalculatorClient {
    List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto, String baseUrl, String finalUrl);

    CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto, String baseUrl);
}
