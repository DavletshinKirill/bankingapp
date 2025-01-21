package dev.davletshin.gateway.web.controller;


import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.gateway.service.CalculatorClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@AllArgsConstructor
@Tag(name = "Calculator Controller", description = "Calculator API")
@Slf4j
public class CalculatorController {

    private final CalculatorClient calculatorClient;
    private static final String CALCULATE_OFFERS = "/calculator/offers";

    private static final String CALCULATOR_URL = "http://localhost:8081";

    @Operation(summary = "getOffers", description = "Create 4 offers")
    @PostMapping("/offers")
    public List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> offerList = calculatorClient.postRequestToCalculateOffers(loanStatementRequestDto, CALCULATOR_URL, CALCULATE_OFFERS);
        offerList.forEach(offer -> log.info(offer.toString()));
        return offerList;
    }

    @Operation(summary = "calculate", description = "Count difference credit")
    @PostMapping("/calc")
    public CreditDto calculate(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        log.info(scoringDataDto.toString());
        CreditDto credit = calculatorClient.postRequestToCalculateCredit(scoringDataDto, CALCULATOR_URL);
        log.info(credit.toString());
        return credit;
    }
}
