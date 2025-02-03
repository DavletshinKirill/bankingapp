package dev.davletshin.dossier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Theme {
    FINISH_REGISTRATION("finish-registration", "Благодарим за регистрацию"),
    CREATE_DOCUMENTS("create-documents", "Создание документа"),
    SEND_DOCUMENTS("send-documents", "Запрос на подписание документа"),
    SEND_SES("send-ses", "Верификация пользователя"),
    CREDIT_ISSUED("credit-issued", "Выдача кредита"),
    STATEMENT_DENIED("statement-denied", "Отклонение запроса");

    private final String topicTitle;
    private final String emailTitle;
}
