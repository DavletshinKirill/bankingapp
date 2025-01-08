package dev.davletshin.deal.domain.statement;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "statement")
@Data
@NoArgsConstructor
@Builder
public class Statement {
    @Id
    @Column(name = "statement_id_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "status_history")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<StatusHistory> statusHistory;

    @Column(name = "applied_offer")
    @JdbcTypeCode(SqlTypes.JSON)
    private LoanOfferDto appliedOffer;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "ses_code")
    private String sesCode;
}
