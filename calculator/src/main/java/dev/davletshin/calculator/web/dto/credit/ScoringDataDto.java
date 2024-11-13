package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScoringDataDto extends LoanStatementRequestDto {

    @NotNull(message = "Пол обязателен")
    private Gender gender;

    @NotNull(message = "Дата выдачи паспорта обязательна")
    private LocalDateTime passportIssueDate;

    @NotNull(message = "Место выдачи паспорта обязательно")
    private String passportIssueBranch;

    @NotNull(message = "Залог обязательно")
    private int dependentAmount;

    private String accountNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private MaritalStatus maritalStatus;
    private EmploymentDto employment;

    public int checkMaritalStatus() {
        int result = 0;
        switch (maritalStatus) {
            case MARRIED -> result = -3;
            case DIVORCED -> result = 1;
        }
        return result;
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
        if (salary.multiply(BigDecimal.valueOf(24L)).compareTo(amount) == -1) {
            throw new RefuseException("The loan amount is too large");
        }
    }

    public int checkGender() {
        int result = 0;
        switch (gender) {
            case FEMALE -> {
                if (isAgeSuitable(32, 60, birthdate)) result = -3;
            }
            case MALE -> {
                if (isAgeSuitable(30, 55, birthdate)) result = -3;
            }
            case NOT_BINARY -> result = 7;
        }
        return result;
    }

    public int checkEmployment() {
        employment.suitableExperience();
        return employment.checkEmploymentPosition() + employment.checkEmploymentStatus();
    }


}
