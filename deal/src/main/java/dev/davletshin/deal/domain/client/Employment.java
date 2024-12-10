package dev.davletshin.deal.domain.client;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employment {

    private UUID id; // employment_uuid
    private EmploymentStatus status; // status
    private String employerINN; // employer_inn
    private BigDecimal salary;

    private EmploymentPosition position;

    private int workExperienceTotal; // work_experience_total

    private int workExperienceCurrent; // work_experience_current
}
