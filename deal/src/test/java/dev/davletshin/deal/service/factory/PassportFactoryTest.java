package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.client.Passport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
class PassportFactoryTest {

    @Autowired
    private PassportFactory passportFactory;

    @Test
    void createNewPassportWithNumberAndSeries() {
        String series = "2020";
        String number = "123456";

        Passport passport = passportFactory.createNewPassportWithNumberAndSeries(series, number);

        assertEquals(series, passport.getSeries());
        assertEquals(number, passport.getNumber());
        assertNull(passport.getIssueDate());
        assertNull(passport.getIssueBranch());
    }

    @Test
    void fullIssueBranchAndDate() {
        Passport passport = passportFactory.createNewPassportWithNumberAndSeries("2020", "123456");
        String issueBranch = "Main Branch";
        LocalDate issueDate = LocalDate.of(2020, 1, 1);

        passportFactory.fillIssueBranchAndDate(passport, issueBranch, issueDate);

        assertEquals(issueBranch, passport.getIssueBranch());
        assertEquals(issueDate, passport.getIssueDate());
    }
}