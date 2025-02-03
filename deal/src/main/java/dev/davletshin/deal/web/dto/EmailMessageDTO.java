package dev.davletshin.deal.web.dto;

import dev.davletshin.deal.domain.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
