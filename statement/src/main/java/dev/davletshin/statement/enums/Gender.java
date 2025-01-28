package dev.davletshin.statement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE(-3), FEMALE(-3), NOT_BINARY(7);

    private final int indexGender;
}
