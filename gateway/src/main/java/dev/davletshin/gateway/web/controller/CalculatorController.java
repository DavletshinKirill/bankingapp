package dev.davletshin.gateway.web.controller;


import dev.davletshin.gateway.service.CalculatorClient;
import dev.davletshin.gateway.web.dto.CreditDto;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import dev.davletshin.gateway.web.dto.ScoringDataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "getOffers", description = "Create 4 offers")
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> offerList = calculatorClient.postRequestToCalculateOffers(loanStatementRequestDto);
        offerList.forEach(offer -> log.info(offer.toString()));
        return ResponseEntity.ok(offerList);
    }

    @Operation(summary = "calculate", description = "Count difference credit")
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculate(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        log.info(scoringDataDto.toString());
        CreditDto credit = calculatorClient.postRequestToCalculateCredit(scoringDataDto);
        log.info(credit.toString());
        return ResponseEntity.ok(credit);
    }
}
