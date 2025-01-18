package dev.davletshin.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Theme {
    FINISH_REGISTRATION("finish-registration", "Thanks for your registration", "Registration"),
    CREATE_DOCUMENTS("create-documents", "Document was created", "Notification"),
    SEND_DOCUMENTS("send-documents", "The request to send documents", "Notification"),
    SEND_SES("send_ses", "The document signing request", "Code confirmation"),
    CREDIT_ISSUED("credit-issued", "The documents to sign", "Notification"),
    STATEMENT_DENIED("statement-denied", "Statement denied", "Notification");

    private final String topicTitle;
    private final String emailTitle;
    private final String bodyTitle;
}
