package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import dev.davletshin.deal.web.mapper.LoanStatementRequestToClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Deal Controller", description = "Deal API")
public class DealController {

    private final DealService dealService;
    private final LoanStatementRequestToClientMapper loanStatementRequestToClientMapper;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping("/statement")
    public List<LoanOfferDto> createClientAndStatement(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        Client client = loanStatementRequestToClientMapper.toEntity(loanStatementRequestDto);
        return dealService.createClientAndStatement(loanStatementRequestDto, client);
    }

    @PostMapping("/offer/select")
    public void updateStatement(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        dealService.updateStatement(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable String statementId) {
        dealService.calculateCredit(statementId, finishRegistrationRequestDto);
    }
}
