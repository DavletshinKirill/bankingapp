package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.service.interfaces.DossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document")
public class DossierController {

    private final DossierService dossierService;

    @PostMapping("/{statementId}/send")
    public ResponseEntity<String> requestSendDocumentEmail(@PathVariable UUID statementId) {
        dossierService.requestSendDocumentEmail(statementId);
        return ResponseEntity.ok("Email was successfully sent");
    }

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<String> requestSignDocument(@PathVariable UUID statementId) {
        dossierService.requestSignDocument(statementId);
        return ResponseEntity.ok("Email was successfully sent");
    }

    @PostMapping("/{statementId}/code")
    public ResponseEntity<String> signCodeDocument(@PathVariable UUID statementId, @RequestBody UUID sesCode) {
        dossierService.signCodeDocument(statementId, sesCode);
        return ResponseEntity.ok("Email was successfully sent");
    }
}
