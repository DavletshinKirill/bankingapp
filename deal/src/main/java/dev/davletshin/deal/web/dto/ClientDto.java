package dev.davletshin.deal.web.dto;

import dev.davletshin.deal.domain.client.Employment;
import dev.davletshin.deal.domain.client.Gender;
import dev.davletshin.deal.domain.client.MaritalStatus;
import dev.davletshin.deal.domain.client.Passport;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClientDto {

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthdate;
    private String email;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private int dependentAmount;
    private String accountNumber;
    private Passport passport;
    private Employment employment;
}
