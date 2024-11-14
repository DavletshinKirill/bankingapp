package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {

    //@Schema(description = "PassportNumber human who takes a loan", example = "123456")
    @NotNull(message = "Статус занятости обязателен")
    private EmploymentStatus employmentStatus;

    //    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    @NotNull(message = "ИНН обязателен")
    private String employerINN;

    //    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    private BigDecimal salary;

    //    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    private Position position;

    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    private int workExperienceTotal;

    @Schema(description = "PassportNumber human who takes a loan", example = "123456")
    private int workExperienceCurrent;

    public int getIndexEmploymentPosition() {
        int result = 0;
        switch (position) {
            case MIDDLE_MANAGER -> result = -2;
            case TOP_MANAGER -> result = -3;
        }
        return result;
    }

    public int getIndexEmploymentStatus() {
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
