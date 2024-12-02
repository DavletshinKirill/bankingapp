package dev.davletshin.deal.web.dto;

import dev.davletshin.deal.domain.client.Gender;
import dev.davletshin.deal.domain.client.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FinishRegistrationRequestDto {
    private Gender gender;
    private MaritalStatus maritalStatus;
    private int dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBrach;
    private EmploymentDto employment;
    private String accountNumber;
}
