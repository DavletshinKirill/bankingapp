package dev.davletshin.deal.domain.credit;

import dev.davletshin.deal.web.dto.PaymentScheduleElementDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "credit")
@Data
@NoArgsConstructor
@Builder
public class Credit {
    @Id
    @Column(name = "credit_id_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;
    private int term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    private BigDecimal rate;
    private BigDecimal psk;

    @Column(name = "payment_schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "salary_client")
    private boolean salaryClient;

    @Column(name = "insurance_enabled")
    private boolean insuranceEnabled;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
}
