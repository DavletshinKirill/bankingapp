package dev.davletshin.gateway.service;


import dev.davletshin.gateway.domain.enums.ApplicationStatus;
import dev.davletshin.gateway.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.gateway.web.dto.LoanOfferDto;
import dev.davletshin.gateway.web.dto.LoanStatementRequestDto;
import dev.davletshin.gateway.web.dto.StatementDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "dealClient", url = "${http.urls.deal}")
public interface DealClient {

    @PostMapping("${http.endpoints.deal.dealStatement}")
    List<LoanOfferDto> createClientAndStatement(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("${http.endpoints.deal.dealOfferSelect}")
    void updateStatement(@RequestBody LoanOfferDto loanOfferDto);

    @PostMapping("${http.endpoints.deal.dealCalculate}/{statementId}")
    void calculateCredit(@Valid @RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable UUID statementId);

    @PostMapping("${http.endpoints.deal.dealDocument}/{statementId}/send")
    void requestSendDocumentEmail(@PathVariable UUID statementId);

    @PostMapping("${http.endpoints.deal.dealDocument}/{statementId}/sign")
    void requestSignDocument(@PathVariable UUID statementId);

    @PostMapping("${http.endpoints.deal.dealDocument}/{statementId}/code")
    void signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode);

    @GetMapping("${http.endpoints.deal.dealAdminStatement}/{statementId}")
    StatementDto getStatementById(@PathVariable UUID statementId);

    @GetMapping("${http.endpoints.deal.dealAdminStatement}")
    List<StatementDto> getAllStatements();

    @PutMapping("${http.endpoints.deal.dealAdminStatement}/{statementId}/status")
    void updateStatementStatus(@PathVariable UUID statementId, @RequestBody ApplicationStatus status);
}
