package dev.davletshin.shared.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EmailMessageDTO {
    private String address;
    private UUID statementId;
    private String text;
    private Theme theme;

    public EmailMessageDTO(final String address, final UUID statementId, final String text, final Theme theme) {
        this.address = address;
        this.statementId = statementId;
        this.text = text;
        this.theme = theme;
    }

    public EmailMessageDTO() {
    }
}
