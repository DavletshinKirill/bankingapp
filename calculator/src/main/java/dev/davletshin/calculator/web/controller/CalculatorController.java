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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Operation(summary = "getOffers", description = "Create 4 offers")
    @PostMapping("/offers")
    public List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        logger.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> offerList = calculateCredit.generateOffers(loanStatementRequestDto);
        offerList.forEach(offer -> logger.info(offer.toString()));
        return offerList;
    }

    @Operation(summary = "calculate", description = "Count difference credit")
    @PostMapping("/calc")
    public CreditDto calculate(@Valid @RequestBody ScoringDataDto scoringDataDto) {
        logger.info(scoringDataDto.toString());
        CreditDto credit = calculateCredit.calculateCredit(scoringDataDto);
        logger.info(credit.toString());
        return credit;
    }

}
