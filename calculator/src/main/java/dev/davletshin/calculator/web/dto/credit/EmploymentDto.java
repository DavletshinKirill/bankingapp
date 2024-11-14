package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {

    @Schema(description = "",
            example = "SELF_EMPLOYED",
            allowableValues = {"UNEMPLOYED", "SELF_EMPLOYED", "BUSINESS_OWNER"})
    @NotNull(message = "Занятость клиента обязательно")
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН клиента", example = "123456789012")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр")
    @NotNull(message = "ИНН обязателен")
    private String employerINN;

    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    private BigDecimal salary;

    @Schema(description = "Должность",
            example = "MIDDLE_MANAGER",
            allowableValues = {"MIDDLE_MANAGER", "TOP_MANAGER"})
    private Position position;

    @Schema(description = "Стаж (месяцы)", example = "20")
    private int workExperienceTotal;

    @Schema(description = "Текущий стаж (месяцы)", example = "6")
    private int workExperienceCurrent;

    public int indexEmploymentPosition() {
        int result = 0;
        switch (position) {
            case MIDDLE_MANAGER -> result = -2;
            case TOP_MANAGER -> result = -3;
        }
        return result;
    }

    public int indexEmploymentStatus() {
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
