package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoringDataDtoTest {

    private ScoringDataDto scopingDataDto;

    @BeforeEach
    public void setUp() {
        scopingDataDto = new ScoringDataDto(
                Gender.MALE,
                LocalDate.of(2020, 1, 1),
                "г. Воронеж",
                3,
                "someString",
                true,
                true,
                MaritalStatus.UNMARRIED,
                new EmploymentDto(
                        EmploymentStatus.UNEMPLOYED,
                        "123456789012",
                        BigDecimal.valueOf(50000),
                        Position.MIDDLE_MANAGER,
                        20,
                        6
                ),
                new BigDecimal("40000"),
                6,
                "Иван",
                "Иванов",
                "Иванович",
                "example@example.com",
                LocalDate.of(1990, 1, 1),
                "2020",
                "123456"
        );
    }

    @Test
    void checkAge() {
        scopingDataDto.checkAge();
        scopingDataDto.setBirthdate(LocalDate.of(2020, 1, 1));
        assertThrows(RefuseException.class, scopingDataDto::checkAge);
        scopingDataDto.setBirthdate(LocalDate.of(1955, 1, 2));
        assertThrows(RefuseException.class, scopingDataDto::checkAge);
    }

    @Test
    void checkAmountSalary() {
        scopingDataDto.checkAmountSalary();
        scopingDataDto.getEmployment().setSalary(BigDecimal.valueOf(1000));
        assertThrows(RefuseException.class, scopingDataDto::checkAmountSalary);
    }

    @Test
    void checkGender() {
        int resultIndex = scopingDataDto.indexGender();
        assertEquals(-3, resultIndex);
        scopingDataDto.setBirthdate(LocalDate.of(2020, 1, 1));
        resultIndex = scopingDataDto.indexGender();
        assertEquals(0, resultIndex);
        scopingDataDto.setBirthdate(LocalDate.of(1950, 1, 1));
        resultIndex = scopingDataDto.indexGender();
        assertEquals(0, resultIndex);
        scopingDataDto.setGender(Gender.FEMALE);
        scopingDataDto.setBirthdate(LocalDate.of(1990, 1, 1));
        resultIndex = scopingDataDto.indexGender();
        assertEquals(-3, resultIndex);
        scopingDataDto.setBirthdate(LocalDate.of(2020, 1, 1));
        resultIndex = scopingDataDto.indexGender();
        assertEquals(0, resultIndex);
        scopingDataDto.setBirthdate(LocalDate.of(1950, 1, 1));
        resultIndex = scopingDataDto.indexGender();
        assertEquals(0, resultIndex);
    }

    @Test
    void checkEmployment() {
        scopingDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        int resultIndex = scopingDataDto.checkEmployment();
        assertEquals(3, resultIndex);
        scopingDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
        resultIndex = scopingDataDto.checkEmployment();
        assertEquals(4, resultIndex);
        scopingDataDto.getEmployment().setPosition(Position.TOP_MANAGER);
        resultIndex = scopingDataDto.checkEmployment();
        assertEquals(3, resultIndex);
    }
}