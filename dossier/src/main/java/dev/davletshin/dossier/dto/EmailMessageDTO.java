package dev.davletshin.dossier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmailMessageDTO {
    private String address;
    private UUID statementId;
    private String text;
    private Theme theme;
}
