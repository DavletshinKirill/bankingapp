package dev.davletshin.gateway.web.dto;



import dev.davletshin.gateway.domain.enums.ApplicationStatus;
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
