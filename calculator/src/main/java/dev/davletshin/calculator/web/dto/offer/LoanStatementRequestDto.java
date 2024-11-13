package dev.davletshin.calculator.web.dto.offer;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.calculator.web.validator.ValidateBirthDate;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class LoanStatementRequestDto {

    @NotNull(message = "Сумма кредита обязательна")
    @DecimalMin(value = "20000", message = "Сумма кредита должны быть больше или равна 20 000")
    protected BigDecimal amount;

    @NotNull(message = "Срок кредита обязателен")
    @Min(value = 6, message = "Срок кредита должен быть больше или равен 6")
    protected int term;

    @NotNull(message = "Имя обязательно")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 латинских букв")
    protected String firstName;

    @NotNull(message = "Фамилия обязательна")
    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 латинских букв")
    protected String lastName;

    @Size(min = 2, max = 30, message = "Отчество должно содержать от 2 до 30 латинских букв")
    protected String middleName;

    @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$",
            message = "Email адрес не соответствует формату.")
    @NotNull(message = "Email обязателен")
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата рождения обязательна")
    @ValidateBirthDate
    protected LocalDate birthdate;

    @NotNull(message = "Серия паспорта обязательна")
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта должна содержать 4 цифры")
    protected String passportSeries;

    @NotNull(message = "Номер паспорта обязателен")
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должна содержать 6 цифр")
    protected String passportNumber;
}
