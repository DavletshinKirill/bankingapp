package dev.davletshin.gateway.web.controller;

import dev.davletshin.gateway.domain.enums.ApplicationStatus;
import dev.davletshin.gateway.service.DealClient;
import dev.davletshin.gateway.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import dev.davletshin.gateway.web.dto.StatementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal")
@Slf4j
@Tag(name = "Deal Controller", description = "Deal API")
@RequiredArgsConstructor
public class DealController {


    private final DealClient dealClient;

    @Value("${http.urls.deal}")
    private String dealUrl;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping("/statement")
    List<LoanOfferDto> createOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> loanOfferDtoList = dealClient.createClientAndStatement(loanStatementRequestDto);
        loanOfferDtoList.forEach(loanOfferDto -> log.info(loanOfferDto.toString()));
        return loanOfferDtoList;
    }

    @Operation(summary = "selectOffer", description = "First update statement")
    @PostMapping("/offer/select")
    void selectOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info(loanOfferDto.toString());
        dealClient.updateStatement(loanOfferDto);
    }

    @Operation(summary = "getStatementById", description = "Get Statement By Id")
    @GetMapping("/admin/statement/{statementId}")
    public StatementDto getStatementById(@PathVariable UUID statementId) {
        StatementDto statementDto = dealClient.getStatementById(statementId);
        log.info("Get Statement By Id: {}", statementDto.toString());
        return statementDto;
    }

    @Operation(summary = "getAllStatements", description = "Get All Statements")
    @GetMapping("/admin/statement")
    public List<StatementDto> getAllStatements() {
        List<StatementDto> statementDtoList = dealClient.getAllStatements();
        log.info("Get All Statements: {}", statementDtoList.toString());
        return statementDtoList;
    }

    @Operation(summary = "updateStatement", description = "second update statement and calculate credit")
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable UUID statementId) {
        log.info(String.valueOf(statementId));
        log.info(finishRegistrationRequestDto.toString());
        dealClient.calculateCredit(finishRegistrationRequestDto, statementId);
    }

    @Operation(summary = "requestSendDocumentEmail", description = "Create 4 offers")
    @PostMapping("/{statementId}/send")
    public void requestSendDocumentEmail(@PathVariable UUID statementId) {
        log.info("Request to send document by statementId: {}", statementId);
        dealClient.requestSendDocumentEmail(statementId);
        log.info("Request to send document by statementId: {} was succeed", statementId);
    }

    @Operation(summary = "requestSignDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/sign")
    public void requestSignDocument(@PathVariable UUID statementId) {
        log.info("Request to sign document by statementId: {}", statementId);
        dealClient.requestSignDocument(statementId);
        log.info("Request to sign document by statementId: {} was succeed", statementId);
    }

    @Operation(summary = "signCodeDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/code")
    public void signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode) {
        log.info("Request to check sesCode document by statementId: {} and sesCode: {}", statementId, sesCode);
        dealClient.signCodeDocument(statementId, sesCode);
        log.info("Request to check sesCode document by statementId: {} and sesCode: {} was succeed", statementId, sesCode);
    }

    @Operation(summary = "updateStatementStatus", description = "Update Statement status")
    @PutMapping("/statement/{statementId}/status")
    public void updateStatementStatus(@PathVariable UUID statementId, @RequestBody ApplicationStatus status) {
        log.info("Update Statement By Id: {}, Status: {}", statementId, status.toString());
        dealClient.updateStatementStatus(statementId, status);
        log.info("Statement By Id: {}, Status: {} successfully updated", statementId, status);
    }

}
