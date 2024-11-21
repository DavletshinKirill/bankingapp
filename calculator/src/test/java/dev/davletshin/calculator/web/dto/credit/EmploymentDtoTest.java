package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmploymentDtoTest {
    private EmploymentDto employmentDto;

    @BeforeEach
    public void setUp() {
        employmentDto = new EmploymentDto(
                EmploymentStatus.SELF_EMPLOYED,
                "123456789012",
                BigDecimal.valueOf(50000),
                Position.MIDDLE_MANAGER,
                20,
                6
        );
    }
    @Test
    void testEmploymentStatusShouldThrowRefuseException() {
        employmentDto.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
        Exception exception = assertThrows(RefuseException.class, employmentDto::checkEmploymentStatus);
        assertEquals("Loans are not given to the unemployed", exception.getMessage());
    }

    @Test
    void testSuitableExperienceShouldThrowRefuseException() {
        employmentDto.setWorkExperienceTotal(10);
        employmentDto.setWorkExperienceCurrent(2);
        Exception exception = assertThrows(RefuseException.class, employmentDto::suitableExperience);
        assertEquals("Unsuitable work experience", exception.getMessage());
    }

    @Test
    void testEmploymentStatusShouldNotThrowException() {
        assertDoesNotThrow(employmentDto::suitableExperience);
    }

    @Test
    void testSuitableExperienceShouldNotThrowException() {
        employmentDto.setWorkExperienceTotal(20);
        employmentDto.setWorkExperienceCurrent(5);
        assertDoesNotThrow(employmentDto::suitableExperience);
    }
}