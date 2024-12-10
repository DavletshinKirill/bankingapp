package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.statement.ApplicationStatus;
import dev.davletshin.deal.domain.statement.ChangeType;
import dev.davletshin.deal.domain.statement.StatusHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
class StatusHistoryFactoryTest {

    @Autowired
    private StatusHistoryFactory statusHistoryFactory;

    @Test
    void createStatusHistory() {

        ApplicationStatus applicationStatus = ApplicationStatus.APPROVED;

        StatusHistory statusHistory = statusHistoryFactory.createStatusHistory(applicationStatus);

        assertEquals(applicationStatus.toString(), statusHistory.getStatus());
        assertEquals(ChangeType.AUTOMATIC, statusHistory.getChangeType());
        assertEquals(LocalDateTime.class, statusHistory.getTime().getClass());
        assertEquals(statusHistory.getTime(), LocalDateTime.now());
    }
}