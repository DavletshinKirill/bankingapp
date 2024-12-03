package dev.davletshin.deal.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.deal.domain.client.Gender;
import dev.davletshin.deal.domain.client.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema($schema = "ScoringData DTO")
public class ScoringDataDto extends LoanStatementRequestDto {

    @Schema(description = "Пол клиента",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE", "NOT_BINARY"})
    @NotNull(message = "Пол обязателен")
    private Gender gender;

    @Schema(description = "Дата выдачи паспорта", example = "2020-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата выдачи паспорта обязательна")
    private LocalDate passportIssueDate;

    @Schema(description = "Место выдачи паспорта", example = "г. Воронеж")
    @Size(min = 5, max = 50, message = "Место выдачи паспорта должно содержать от 5 до 50 символов")
    @NotBlank(message = "Место выдачи паспорта обязательно")
    private String passportIssueBranch;

    @Schema(description = "Количество поручителей", example = "3")
    @NotNull(message = "Количество поручителей не должно быть равно нулю")
    private int dependentAmount;

    @Schema(description = "Аккаунт клиента", example = "someString")
    @NotBlank(message = "Аккаунт клиента не дожен быть пустой")
    private String accountNumber;

    @Schema(description = "Застрахованность клиента", example = "true")
    @NotNull(message = "Застрахованность клиента не дожна быть пустой")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Трудоустроенность клиента", example = "true")
    @NotNull(message = "Трудоустроенность клиента не дожна быть пустой")
    private Boolean isSalaryClient;

    @Schema(description = "Семейной положение клиента",
            example = "UNMARRIED",
            allowableValues = {"UNMARRIED", "MARRIED", "DIVORCED"})
    @NotNull(message = "Семейное положение клиента обязательно")
    private MaritalStatus maritalStatus;

    private EmploymentDto employment;

    public void setLoanStatementRequestDto(LoanStatementRequestDto loanStatementRequestDto) {
        this.amount = loanStatementRequestDto.getAmount();
        this.term = loanStatementRequestDto.getTerm();
        this.firstName = loanStatementRequestDto.getFirstName();
        this.lastName = loanStatementRequestDto.getLastName();
        this.middleName = loanStatementRequestDto.getMiddleName();
        this.birthdate = loanStatementRequestDto.getBirthdate();
        this.setEmail(loanStatementRequestDto.getEmail());
        this.passportNumber = loanStatementRequestDto.getPassportNumber();
        this.passportSeries = loanStatementRequestDto.getPassportSeries();
    }
}
