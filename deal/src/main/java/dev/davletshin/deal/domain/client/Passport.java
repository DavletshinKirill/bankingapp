package dev.davletshin.deal.domain.client;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Passport {
    @Id
    @Column(name = "passport_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String series;
    private String number;
    @Column(name = "issue_branch")
    private String issueBranch;
    @Column(name = "issue_date")
    private LocalDate issueDate;
}
