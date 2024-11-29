package dev.davletshin.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MaritalStatus {
    UNMARRIED(-3),
    MARRIED(-3),
    DIVORCED(1);

    private final int indexMaritalStatus;
}
