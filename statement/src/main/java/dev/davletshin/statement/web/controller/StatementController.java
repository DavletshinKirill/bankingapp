package dev.davletshin.statement.web.controller;

import dev.davletshin.statement.service.DealClient;
import dev.davletshin.statement.web.dto.LoanOfferDto;
import dev.davletshin.statement.web.dto.LoanStatementRequestDto;
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
@RequestMapping("/statement")
@RequiredArgsConstructor
@Tag(name = "Statement Controller", description = "Statement API")
@Slf4j
public class StatementController {

    private final DealClient dealClient;
    private static final String LOAN_RESULT = "Request was successful";

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> createOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> loanOfferDtoList = dealClient.createOffers(loanStatementRequestDto);
        loanOfferDtoList.forEach(loanOfferDto -> log.info(loanOfferDto.toString()));
        return ResponseEntity.ok(loanOfferDtoList);
    }

    @Operation(summary = "selectOffer", description = "First update statement")
    @PostMapping("/offer")
    public void selectOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info(loanOfferDto.toString());
        dealClient.updateOffer(loanOfferDto);
        log.info(LOAN_RESULT);
    }

}
