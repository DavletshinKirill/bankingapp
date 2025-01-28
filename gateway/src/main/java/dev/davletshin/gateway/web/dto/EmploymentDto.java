package dev.davletshin.gateway.web.dto;

import dev.davletshin.gateway.domain.enums.EmploymentPosition;
import dev.davletshin.gateway.domain.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmploymentDto {

    @Schema(description = "Занятость клиента",
            example = "SELF_EMPLOYED",
            allowableValues = {"UNEMPLOYED", "SELF_EMPLOYED", "BUSINESS_OWNER"})
    @NotNull(message = "Занятость клиента обязательно")
    private EmploymentStatus employmentStatus;

    @Schema(description = "ИНН клиента", example = "123456789012")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр")
    @NotBlank(message = "ИНН обязателен")
    private String employerINN;

    @Schema(description = "Заработная плата клиента", example = "20000")
    private BigDecimal salary;

    @Schema(description = "Должность",
            example = "MIDDLE_MANAGER",
            allowableValues = {"MIDDLE_MANAGER", "TOP_MANAGER"})
    private EmploymentPosition position;

    @Schema(description = "Стаж (месяцы)", example = "20")
    private int workExperienceTotal;

    @Schema(description = "Текущий стаж (месяцы)", example = "6")
    private int workExperienceCurrent;
}
