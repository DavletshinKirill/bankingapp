package dev.davletshin.calculator.web.dto.offer;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.calculator.web.validator.ValidateBirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema($schema = "LoanStatementRequest DTO")
public class LoanStatementRequestDto {

    @Schema(description = "Сумма", example = "40000")
    @NotNull(message = "Сумма кредита обязательна")
    @DecimalMin(value = "20000", message = "Сумма кредита должны быть больше или равна 20 000", inclusive = false)
    protected BigDecimal amount;

    @Schema(description = "Срок на который берется кредит", example = "6")
    @NotNull(message = "Срок кредита обязателен")
    @Min(value = 6, message = "Срок кредита должен быть больше или равен 6")
    protected int term;

    @Schema(description = "Имя клиента", example = "Иван")
    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 латинских букв")
    protected String firstName;

    @Schema(description = "Фамилия клиента", example = "Иванов")
    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 латинских букв")
    protected String lastName;

    @Schema(description = "Отчество клиента", example = "Иванович")
    @Size(min = 2, max = 30, message = "Отчество должно содержать от 2 до 30 латинских букв")
    protected String middleName;

    @Schema(description = "Email клиента", example = "example@example.com")
    @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$",
            message = "Email адрес не соответствует формату.")
    @NotBlank(message = "Email обязателен")
    private String email;

    @Schema(description = "Дата рождения клиента", example = "1990-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата рождения обязательна")
    @ValidateBirthDate
    protected LocalDate birthdate;

    @Schema(description = "Серия паспорта клиента", example = "2020")
    @NotBlank(message = "Серия паспорта обязательна")
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта должна содержать 4 цифры")
    protected String passportSeries;

    @Schema(description = "Номер паспорта клиента", example = "123456")
    @NotBlank(message = "Номер паспорта обязателен")
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должна содержать 6 цифр")
    protected String passportNumber;
}
