package dev.davletshin.deal.domain.statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class StatusHistory {
    private String status;


    private ChangeType changeType; // change_type
    private Timestamp time;
}
