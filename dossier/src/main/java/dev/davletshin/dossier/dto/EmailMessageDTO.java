package dev.davletshin.dossier.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDTO {
    private String address;
    private UUID statementId;
    private String text;
    private Theme theme;
}
