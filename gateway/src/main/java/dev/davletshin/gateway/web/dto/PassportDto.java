package dev.davletshin.gateway.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PassportDto {

    private UUID id;
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
