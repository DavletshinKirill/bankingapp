package dev.davletshin.deal.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MaritalStatus {
    SINGLE(-3),
    MARRIED(-3),
    WIDOW_WIDOWER(0),
    DIVORCED(1);

    private final int indexMaritalStatus;
}
