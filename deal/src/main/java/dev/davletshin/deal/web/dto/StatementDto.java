package dev.davletshin.deal.web.dto;

import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.statement.StatusHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StatementDto {

    private ApplicationStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime signDate;
    private List<StatusHistory> statusHistory;
    private LoanOfferDto appliedOffer;
    private CreditDto credit;
    private ClientDto client;
    private UUID sesCode;
}
