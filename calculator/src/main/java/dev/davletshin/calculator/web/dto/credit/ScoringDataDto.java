package dev.davletshin.calculator.web.dto.credit;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    @NotNull(message = "Место выдачи паспорта обязательно")
    private String passportIssueBranch;

    @Schema(description = "Количество поручителей", example = "3")
    @NotNull(message = "Количество поручителей не должно быть равно нулю")
    private int dependentAmount;

    @Schema(description = "Аккаунт клиента", example = "someString")
    @NotNull(message = "Аккаунт клиента не дожен быть пустой")
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

    public void checkAge() {
        if (!isAgeSuitable(20, 65, birthdate)) throw new RefuseException("The Wrong Age");
    }

    private boolean isAgeSuitable(int startAge, int endAge, LocalDate date) {
        Period age = Period.between(date, LocalDate.now());
        int amount_age = age.getYears();
        return (startAge <= amount_age) && (amount_age <= endAge);
    }

    public void checkAmountSalary() {
        BigDecimal salary = employment.getSalary();
        if (salary.multiply(BigDecimal.valueOf(24L)).compareTo(amount) < 0) {
            throw new RefuseException("The loan amount is too large");
        }
    }

    public int indexGender() {
        switch (gender) {
            case FEMALE -> {
                if (!isAgeSuitable(32, 60, birthdate)) return 0;
            }
            case MALE -> {
                if (!isAgeSuitable(30, 55, birthdate)) return 0;
            }
        }
        return gender.getIndexGender();
    }

    public int checkEmployment() {
        employment.suitableExperience();
        employment.checkEmploymentStatus();
        return employment.getPosition().getIndexEmployment() + employment.getEmploymentStatus().getIndexEmployment();
    }


}
