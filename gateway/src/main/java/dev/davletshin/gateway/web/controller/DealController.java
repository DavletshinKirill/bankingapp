package dev.davletshin.gateway.web.controller;

import dev.davletshin.calculator.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.gateway.service.CalculatorClient;
import dev.davletshin.gateway.service.DealClient;
import dev.davletshin.gateway.service.StatementClient;
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

    private final static String DEAL_STATEMENT = "/deal/statement";
    private final static String OFFER_SELECT = "/deal/offer/select";

    private final CalculatorClient calculatorClient;
    private final StatementClient statementClient;
    private final DealClient dealClient;

    @Value("${http.urls.deal}")
    private String dealUrl;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping("/statement")
    List<LoanOfferDto> createOffers(@Valid @RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info(loanStatementRequestDto.toString());
        List<LoanOfferDto> loanOfferDtoList = calculatorClient.postRequestToCalculateOffers(loanStatementRequestDto, dealUrl, DEAL_STATEMENT);
        loanOfferDtoList.forEach(loanOfferDto -> log.info(loanOfferDto.toString()));
        return loanOfferDtoList;
    }

    @Operation(summary = "selectOffer", description = "First update statement")
    @PostMapping("/offer/select")
    void selectOffer(@Valid @RequestBody LoanOfferDto loanOfferDto) {
        log.info(loanOfferDto.toString());
        statementClient.updateOffer(loanOfferDto, dealUrl, OFFER_SELECT);
    }

//    @Operation(summary = "getStatementById", description = "Get Statement By Id")
//    @GetMapping("/admin/statement/{statementId}")
//    public StatementDto getStatementById(@PathVariable UUID statementId) {
//        StatementDto statementDto = dealClient.getStatement(statementId);
//        log.info("Get Statement By Id: {}", statementDto.toString());
//        return statementDto;
//    }

//    @Operation(summary = "getAllStatements", description = "Get All Statements")
//    @GetMapping("/admin/statement")
//    public List<StatementDto> getAllStatements() {
//        List<StatementDto> statementDtoList = dealClient.getStatements();
//        log.info("Get All Statements: {}", statementDtoList.toString());
//        return statementDtoList;
//    }

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
        log.info("Request statementId: {}", statementId);
        String finalUrl = "/" + statementId.toString() + "/send";
        dealClient.sendDocs(statementId, finalUrl);
        log.info("Message to kafka was sent");
    }

    @Operation(summary = "requestSignDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/sign")
    public void requestSignDocument(@PathVariable UUID statementId) {
        log.info("Request statementId: {}", statementId);
        String finalUrl = "/" + statementId.toString() + "/sign";
        dealClient.sendDocs(statementId, finalUrl);
        log.info("Message to kafka was sent");
    }

    @Operation(summary = "signCodeDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/code")
    public void signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode) {
        log.info("Request statementId: {}", statementId);
        String finalUrl = "/" + statementId.toString() + "/code";
        dealClient.checkCode(statementId, sesCode, finalUrl);
        log.info("Message to kafka was sent");
    }

}
