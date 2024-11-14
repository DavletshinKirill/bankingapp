package dev.davletshin.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OffersCreation {
    NONE(1, 1, false, false),
    INSURANCE_ENABLED(-1, 2, true, false),
    SALARY_CLIENT(1, -2, false, true),
    SALARY_CLIENT_AND_INSURANCE_ENABLED(-1, -1, true, true);

    private final int insuranceRate;
    private final int salaryClient;
    private final boolean insuranceEnabled;
    private final boolean salaryClientEnabled;

}
