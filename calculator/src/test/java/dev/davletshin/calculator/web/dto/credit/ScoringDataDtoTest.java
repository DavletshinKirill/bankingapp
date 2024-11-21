package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.MaritalStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ScoringDataDtoTest {

    private ScoringDataDto scoringDataDto;

    @BeforeEach
    public void setUp() {
        scoringDataDto = new ScoringDataDto(
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
    void testCheckAgeShouldThrowRefuseException() {
        scoringDataDto.setBirthdate(LocalDate.of(2005, 1, 1));
        Exception exception = assertThrows(RefuseException.class, scoringDataDto::checkAge);
        assertEquals("The Wrong Age", exception.getMessage());
    }

    @Test
    void testCheckAgeShouldNotThrowRefuseException() {
        scoringDataDto.setBirthdate(LocalDate.of(1990, 1, 1));
        assertDoesNotThrow(scoringDataDto::checkAge);
    }

    @Test
    void testCheckAmountSalaryShouldThrowRefuseException() {
        scoringDataDto.getEmployment().setSalary(BigDecimal.valueOf(500));
        Exception exception = assertThrows(RefuseException.class, scoringDataDto::checkAmountSalary);
        assertEquals("The loan amount is too large", exception.getMessage());
    }

    @Test
    void testCheckAmountSalaryShouldNotThrowException() {
        scoringDataDto.getEmployment().setSalary(BigDecimal.valueOf(10000));
        assertDoesNotThrow(scoringDataDto::checkAmountSalary);
    }

    @Test
    void testCheckEmploymentShouldReturnEmploymentIndex() {
        scoringDataDto.getEmployment().setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
        scoringDataDto.getEmployment().setPosition(Position.MIDDLE_MANAGER);
        assertDoesNotThrow(scoringDataDto::checkEmployment);
        assertEquals(4, scoringDataDto.checkEmployment());
    }

    @ParameterizedTest
    @EnumSource(Gender.class)
    void testIndexGenderShouldReturnIndexGender(Gender gender) {
        scoringDataDto.setBirthdate(LocalDate.of(1990, 1, 1));
        scoringDataDto.setGender(gender);
        assertEquals(gender.getIndexGender(), scoringDataDto.getGender().getIndexGender());
    }

    @Test
    void testIndexMaleGenderShouldReturnNull() {
        scoringDataDto.setBirthdate(LocalDate.of(2000, 1, 1));
        scoringDataDto.setGender(Gender.MALE);
        assertEquals(0, scoringDataDto.indexGender());
    }

    @Test
    void testIndexFemaleGenderShouldReturnNull() {
        scoringDataDto.setBirthdate(LocalDate.of(2000, 1, 1));
        scoringDataDto.setGender(Gender.FEMALE);
        assertEquals(0, scoringDataDto.indexGender());
    }
}