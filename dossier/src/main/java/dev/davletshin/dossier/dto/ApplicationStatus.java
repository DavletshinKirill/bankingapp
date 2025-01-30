package dev.davletshin.dossier.dto;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    PREAPPROVAL,
    APPROVED,
    CC_DENIED,
    CC_APPROVED,
    PREPARE_DOCUMENTS,
    DOCUMENT_CREATED,
    DOCUMENT_SIGNED,
    CREDIT_ISSUED;

    public ApplicationStatus next() {
        return switch (this) {
            case PREAPPROVAL -> APPROVED;
            case APPROVED -> PREPARE_DOCUMENTS;
            case CC_DENIED -> CC_DENIED;
            case CC_APPROVED -> CC_APPROVED;
            case PREPARE_DOCUMENTS -> DOCUMENT_CREATED;
            case DOCUMENT_CREATED -> DOCUMENT_SIGNED;
            case DOCUMENT_SIGNED, CREDIT_ISSUED -> CREDIT_ISSUED;
        };
    }
}
