package dev.davletshin.gateway.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.gateway.domain.enums.Gender;
import dev.davletshin.gateway.domain.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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
    @NotBlank(message = "Аккаунт клиента не должен быть пустой")
    private String accountNumber;

    @Schema(description = "Страховка клиента", example = "true")
    @NotNull(message = "Страховка клиента не должен быть пустой")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Трудоустройство клиента", example = "true")
    @NotNull(message = "Трудоустройство клиента не должен быть пустой")
    private Boolean isSalaryClient;

    @Schema(description = "Семейной положение клиента",
            example = "SINGLE",
            allowableValues = {"SINGLE", "MARRIED", "DIVORCED", "WIDOW_WIDOWER"})
    @NotNull(message = "Семейное положение клиента обязательно")
    private MaritalStatus maritalStatus;

    private EmploymentDto employment;

    public ScoringDataDto(Gender gender, LocalDate passportIssueDate, String passportIssueBranch, int dependentAmount,
                          String accountNumber, boolean isInsuranceEnabled, boolean isSalaryClient, MaritalStatus maritalStatus,
                          EmploymentDto employment, BigDecimal amount, int term, String firstName,
                          String lastName, String middleName, String email, LocalDate birthdate,
                          String passportSeries, String passportNumber) {
        super(amount, term, firstName, lastName, middleName, email, birthdate, passportSeries, passportNumber);
        this.gender = gender;
        this.passportIssueDate = passportIssueDate;
        this.passportIssueBranch = passportIssueBranch;
        this.dependentAmount = dependentAmount;
        this.accountNumber = accountNumber;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        this.maritalStatus = maritalStatus;
        this.employment = employment;
    }

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
