package dev.davletshin.calculator.web.controller;

import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Calculator Controller", description = "Calculator API")
public class CalculatorController {

    private final CalculateService calculateCredit;

    @Operation(summary = "getOffers", description = "Create 4 offers")
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> getOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> offerList = calculateCredit.generateOffers(loanStatementRequestDto);
        offerList.forEach(offer -> log.info(offer.toString()));
        return ResponseEntity.ok(offerList);
    }

    @Operation(summary = "calculate", description = "Count difference credit")
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculate(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        log.info(scoringDataDto.toString());
        CreditDto credit = calculateCredit.calculateCredit(scoringDataDto);
        log.info(credit.toString());
        return ResponseEntity.ok(credit);
    }

}
