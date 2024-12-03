package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.domain.statement.ApplicationStatus;
import dev.davletshin.deal.domain.statement.ChangeType;
import dev.davletshin.deal.domain.statement.StatusHistory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class StatusHistoryFactory {
    public StatusHistory createStatusHistory(ApplicationStatus applicationStatus) {
        return StatusHistory.builder()
                .status(applicationStatus.toString())
                .changeType(ChangeType.AUTOMATIC)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
