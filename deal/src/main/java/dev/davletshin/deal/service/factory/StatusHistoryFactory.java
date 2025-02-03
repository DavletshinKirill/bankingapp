package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.enums.ChangeType;
import dev.davletshin.deal.domain.statement.StatusHistory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StatusHistoryFactory {
    public StatusHistory createStatusHistory(ApplicationStatus applicationStatus) {
        return StatusHistory.builder()
                .status(applicationStatus.toString())
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .build();
    }
}
