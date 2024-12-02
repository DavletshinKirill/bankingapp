package dev.davletshin.deal.domain.statement;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class StatusHistory {
    private String status;

    @Column(name = "change_type")
    private ChangeType changeType;
    private Timestamp time;
}
