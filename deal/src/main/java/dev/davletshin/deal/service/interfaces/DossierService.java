package dev.davletshin.deal.service.interfaces;

import java.util.UUID;

public interface DossierService {
    void requestSendDocumentEmail(UUID statementId);

    void requestSignDocument(UUID statementId);

    void signCodeDocument(UUID statementId, UUID sesCode);
}
