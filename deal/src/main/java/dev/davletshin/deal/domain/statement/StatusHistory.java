package dev.davletshin.deal.domain.statement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Embeddable
@NoArgsConstructor
public class StatusHistory {
    private String status;
    @Column(name = "change_type")
    private ChangeType changeType;
    private LocalDateTime time;
}
