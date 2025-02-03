package dev.davletshin.deal.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmploymentPosition {
    WORKER(0),
    MIDDLE_MANAGER(-2),
    TOP_MANAGER(-3),
    OWNER(0);

    private final int indexEmployment;
}
