package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import dev.davletshin.deal.web.mapper.LoanStatementRequestToClientMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Deal Controller", description = "Deal API")
@Slf4j
public class DealController {

    private final DealService dealService;
    private final LoanStatementRequestToClientMapper loanStatementRequestToClientMapper;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping("/statement")
    public List<LoanOfferDto> createClientAndStatement(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        Client client = loanStatementRequestToClientMapper.toEntity(loanStatementRequestDto);
        List<LoanOfferDto> loanOfferDtoList = dealService.createClientAndStatement(loanStatementRequestDto, client);
        loanOfferDtoList.forEach(loanOfferDto -> log.info(loanOfferDto.toString()));
        return loanOfferDtoList;
    }

    @Operation(summary = "updateStatement", description = "first update statement")
    @PostMapping("/offer/select")
    public void updateStatement(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info(loanOfferDto.toString());
        Statement statement = dealService.updateStatement(loanOfferDto);
        log.info(statement.toString());
    }

    @Operation(summary = "updateStatement", description = "second update statement and calculate credit")
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable String statementId) {
        log.info(statementId);
        log.info(finishRegistrationRequestDto.toString());
        Statement statement = dealService.calculateCredit(statementId, finishRegistrationRequestDto);
        log.info(statement.toString());
    }
}
