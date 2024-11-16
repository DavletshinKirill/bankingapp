package dev.davletshin.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Position {
    MIDDLE_MANAGER(2),
    TOP_MANAGER(1);

    private final int indexEmployment;
}
