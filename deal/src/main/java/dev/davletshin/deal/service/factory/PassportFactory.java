package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.domain.client.Passport;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class PassportFactory {

    public Passport createNewPassportWithNumberAndSeries(String series, String number) {
        return Passport.builder()
                .id(UUID.randomUUID())
                .series(series)
                .number(number)
                .build();
    }

    public Passport fillIssueBranchAndDate(Passport passport, String issueBranch, LocalDate issueDate) {
        passport.setIssueDate(issueDate);
        passport.setIssueBranch(issueBranch);
        return passport;
    }
}
