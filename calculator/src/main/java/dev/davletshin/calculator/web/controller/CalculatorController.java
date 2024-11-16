package dev.davletshin.calculator.web.controller;

import dev.davletshin.calculator.domain.LogLevel;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.service.LogData;
import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
@Tag(name = "Calculator Controller", description = "Calculator API")
public class CalculatorController {

    private final CalculateService calculateCredit;
    private final LogData logData = LogData.getInstance();

    @Operation(summary = "getOffers", description = "Create 4 offers")
    @PostMapping("/offers")
    public List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        logData.logInfo(loanStatementRequestDto, "Request", LogLevel.INFO);
        List<LoanOfferDto> offerList = calculateCredit.generateOffers(loanStatementRequestDto);
        offerList.forEach(offer -> logData.logInfo(offer, "Response", LogLevel.INFO));
        return offerList;
    }

    @Operation(summary = "calculate", description = "Count difference credit")
    @PostMapping("/calc")
    public CreditDto calculate(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        logData.logInfo(scoringDataDto, "Request", LogLevel.INFO);
        CreditDto credit = calculateCredit.calculateCredit(scoringDataDto);
        logData.logInfo(credit, "Response", LogLevel.INFO);
        return credit;
    }

}
