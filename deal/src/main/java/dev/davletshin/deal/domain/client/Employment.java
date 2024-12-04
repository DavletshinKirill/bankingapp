package dev.davletshin.deal.domain.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employment {
    @Id
    @Column(name = "employment_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Column(name = "employer_inn")
    private String employerInn;
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private EmploymentPosition position;

    @Column(name = "work_experience_total")
    private int workExperienceTotal;

    @Column(name = "work_experience_current")
    private int workExperienceCurrent;
}
