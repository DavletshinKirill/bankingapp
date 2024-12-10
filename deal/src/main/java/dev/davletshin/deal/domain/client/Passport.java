package dev.davletshin.deal.domain.client;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Passport {

    private UUID id; // passport_uuid
    private String series;
    private String number;
    private String issueBranch; // issue_branch
    private LocalDate issueDate; // issue_date
}
