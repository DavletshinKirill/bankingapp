package dev.davletshin.calculator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Position {
    WORKER(0),
    MIDDLE_MANAGER(-2),
    TOP_MANAGER(-3),
    OWNER(0);

    private final int indexEmployment;
}
