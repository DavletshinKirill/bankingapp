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

    @Operation(summary = "requestSendDocumentEmail", description = "Send document by statementId")
    @PostMapping("/{statementId}/send")
    public void requestSendDocumentEmail(@PathVariable UUID statementId) {
        log.info("Request to send document by statementId: {}", statementId);
        dossierService.requestSendDocumentEmail(statementId);
        log.info("Request to send document by statementId: {} was succeed", statementId);
    }

    @Operation(summary = "requestSignDocument", description = "Sign document by statementId")
    @PostMapping("/{statementId}/sign")
    public void requestSignDocument(@PathVariable UUID statementId) {
        log.info("Request to sign document by statementId: {}", statementId);
        dossierService.requestSignDocument(statementId);
        log.info("Request to sign document by statementId: {} was succeed", statementId);
    }

    @Operation(summary = "signCodeDocument", description = "Check sesCode by statementId")
    @PostMapping("/{statementId}/code")
    public void signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode) {
        log.info("Request to check sesCode document by statementId: {} and sesCode: {}", statementId, sesCode);
        dossierService.signCodeDocument(statementId, sesCode);
        log.info("Request to check sesCode document by statementId: {} and sesCode: {} was succeed", statementId, sesCode);
    }
}
