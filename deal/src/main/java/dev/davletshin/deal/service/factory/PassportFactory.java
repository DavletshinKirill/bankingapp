package dev.davletshin.deal.service.factory;

import dev.davletshin.deal.domain.client.Passport;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PassportFactory {

    public Passport createNewPassportWithNumberAndSeries(String series, String number) {
        return Passport.builder()
                .series(series)
                .number(number)
                .build();
    }

    public Passport fullIssueBranchAndDate(Passport passport, String issueBranch, LocalDate issueDate) {
        passport.setIssueDate(issueDate);
        passport.setIssueBranch(issueBranch);
        return passport;
    }
}
