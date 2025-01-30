package dev.davletshin.dossier.service;

import dev.davletshin.dossier.dto.ApplicationStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "dealClient", url = "${http.urls.deal}")
public interface DealClient {
    @PutMapping("${http.urls.endpoint}/{statementId}/status")
    void updateStatementStatus(@PathVariable UUID statementId, @RequestBody ApplicationStatus status);
}
