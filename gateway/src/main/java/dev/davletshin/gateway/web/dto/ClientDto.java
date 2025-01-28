package dev.davletshin.gateway.web.dto;


import dev.davletshin.gateway.domain.enums.Gender;
import dev.davletshin.gateway.domain.enums.MaritalStatus;
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
    private PassportDto passport;
    private EmploymentDto employment;
}
