package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.service.interfaces.DossierSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document")
public class DossierController {

    private final DossierSender dossierSender;

    @PostMapping("/{statementId}/send")
    public ResponseEntity<String> sendEmail(@PathVariable UUID statementId) {
        return ResponseEntity.ok("Email was successfully sent");
    }

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<String> requestSignDocument(@PathVariable UUID statementId) {
        return ResponseEntity.ok("Email was successfully sent");
    }

    @PostMapping("/{statementId}/code")
    public ResponseEntity<String> signDocument(@PathVariable UUID statementId) {
        return ResponseEntity.ok("Email was successfully sent");
    }
}
