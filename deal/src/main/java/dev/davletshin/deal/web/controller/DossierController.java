package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.service.interfaces.DossierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document")
@Tag(name = "Dossier Controller", description = "Dossier API")
@Slf4j
public class DossierController {

    private final DossierService dossierService;

    @Operation(summary = "requestSendDocumentEmail", description = "Create 4 offers")
    @PostMapping("/{statementId}/send")
    public void requestSendDocumentEmail(@PathVariable UUID statementId) {
        log.info("Request statementId: {}", statementId);
        dossierService.requestSendDocumentEmail(statementId);
        log.info("Message to kafka was sent");
    }

    @Operation(summary = "requestSignDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/sign")
    public void requestSignDocument(@PathVariable UUID statementId) {
        log.info("Request statementId: {}", statementId);
        dossierService.requestSignDocument(statementId);
        log.info("Message to kafka was sent");
    }

    @Operation(summary = "signCodeDocument", description = "Create 4 offers")
    @PostMapping("/{statementId}/code")
    public void signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode) {
        log.info("Request statementId: {}", statementId);
        dossierService.signCodeDocument(statementId, sesCode);
        log.info("Message to kafka was sent");
    }
}
