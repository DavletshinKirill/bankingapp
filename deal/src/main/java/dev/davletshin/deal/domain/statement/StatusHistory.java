package dev.davletshin.deal.domain.statement;

import dev.davletshin.deal.domain.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StatusHistory {
    private String status;
    private ChangeType changeType;
    private LocalDateTime time;
}
