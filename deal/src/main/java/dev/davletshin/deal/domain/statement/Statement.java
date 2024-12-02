package dev.davletshin.deal.domain.statement;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.credit.Credit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
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

    private ApplicationStatus status;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "sign_date")
    private Timestamp signDate;

    @Column(name = "employment_id")
    @JdbcTypeCode(SqlTypes.JSON)
    private StatusHistory statusHistory;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
