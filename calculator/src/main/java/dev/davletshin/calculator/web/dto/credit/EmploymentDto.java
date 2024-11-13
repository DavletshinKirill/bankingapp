package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    @NotNull(message = "Статус занятости обязателен")
    private EmploymentStatus employmentStatus;

    @NotNull(message = "ИНН обязателен")
    private String employerINN;

    private BigDecimal salary;
    private Position position;
    private int workExperienceTotal;
    private int workExperienceCurrent;

    public int checkEmploymentPosition() {
        int result = 0;
        switch (position) {
            case MIDDLE_MANAGER -> result = -2;
            case TOP_MANAGER -> result = -3;
        }
        return result;
    }

    public int checkEmploymentStatus() {
        int result = 0;
        switch (employmentStatus) {
            case UNEMPLOYED -> throw new RefuseException("Loans are not given to the unemployed");
            case SELF_EMPLOYED -> result = 2;
            case BUSINESS_OWNER -> result = 1;
        }
        return result;
    }

    public void suitableExperience() {
        if ((workExperienceTotal < 18) || (workExperienceCurrent < 3))
            throw new RefuseException("Unsuitable work experience");
    }
}
