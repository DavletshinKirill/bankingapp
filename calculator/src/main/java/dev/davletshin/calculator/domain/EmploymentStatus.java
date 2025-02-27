package dev.davletshin.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmploymentStatus {
    UNEMPLOYED(0),
    SELF_EMPLOYED(2),
    EMPLOYED(0),
    BUSINESS_OWNER(1);

    private final int indexEmployment;
}
