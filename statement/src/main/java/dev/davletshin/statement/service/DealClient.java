package dev.davletshin.statement.service;



import dev.davletshin.statement.web.dto.LoanOfferDto;
import dev.davletshin.statement.web.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "dealClient", url = "${http.base.url}")
public interface DealClient {

    @PostMapping("${http.endpoints.createOffers}")
    List<LoanOfferDto> createOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("${http.endpoints.selectOffer}")
    void updateOffer(LoanOfferDto loanOfferDto);
}
