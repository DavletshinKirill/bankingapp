package dev.davletshin.deal.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishRegistrationRequestDto {
    @Schema(description = "Пол клиента",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE", "NOT_BINARY"})
    @NotNull(message = "Пол обязателен")
    private Gender gender;

    @Schema(description = "Семейной положение клиента",
            example = "MARRIED",
            allowableValues = {"SINGLE", "WIDOW_WIDOWER", "MARRIED", "DIVORCED"})
    @NotNull(message = "Семейное положение клиента обязательно")
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество поручителей", example = "3")
    @NotNull(message = "Количество поручителей не должно быть равно нулю")
    private int dependentAmount;

    @Schema(description = "Дата выдачи паспорта", example = "2020-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата выдачи паспорта обязательна")
    private LocalDate passportIssueDate;

    @Schema(description = "Место выдачи паспорта", example = "г. Воронеж")
    @Size(min = 5, max = 50, message = "Место выдачи паспорта должно содержать от 5 до 50 символов")
    @NotBlank(message = "Место выдачи паспорта обязательно")
    private String passportIssueBranch;

    private EmploymentDto employment;

    @Schema(description = "Аккаунт клиента", example = "someString")
    @NotBlank(message = "Аккаунт клиента не должен быть пустой")
    private String accountNumber;
}
