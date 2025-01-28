package dev.davletshin.gateway.web.controller;

import dev.davletshin.gateway.service.CalculatorClient;
import dev.davletshin.gateway.service.StatementClient;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statement")
@RequiredArgsConstructor
@Tag(name = "Statement Controller", description = "Statement API")
@Slf4j
public class StatementController {

    private final StatementClient statementClient;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping
    List<LoanOfferDto> createOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> loanOfferDtoList = statementClient.createOffers(loanStatementRequestDto);
        loanOfferDtoList.forEach(loanOfferDto -> log.info(loanOfferDto.toString()));
        return loanOfferDtoList;
    }

    @Operation(summary = "selectOffer", description = "First update statement")
    @PostMapping("/offer")
    void selectOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info(loanOfferDto.toString());
        statementClient.selectOffer(loanOfferDto);
    }
}
