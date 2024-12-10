package dev.davletshin.deal.domain.client;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Employment {

    @Column(name = "employment_uuid")
    private UUID id;
    @Column(name = "status")
    private EmploymentStatus employmentStatus;
    @Column(name = "employer_inn")
    private String employerINN;
    private BigDecimal salary;

    private EmploymentPosition position;

    private int workExperienceTotal;

    private int workExperienceCurrent;
}
