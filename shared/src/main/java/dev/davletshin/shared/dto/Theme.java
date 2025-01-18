package dev.davletshin.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Theme {
    FINISH_REGISTRATION("finish-registration", "Thanks for your registration"),
    CREATE_DOCUMENTS("create-documents", "Document was created"),
    SEND_DOCUMENTS("send-documents", "The request to send documents"),
    SEND_SES("send_ses", "The document signing request"),
    CREDIT_ISSUED("credit-issued", "The documents to sign"),
    STATEMENT_DENIED("statement-denied", "Statement denied");

    private final String topicTitle;
    private final String emailTitle;
}
