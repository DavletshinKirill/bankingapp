package dev.davletshin.calculator.web.dto.credit;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmploymentDtoTest {
    private EmploymentDto employmentDto;

    @BeforeEach
    public void setUp() {
        employmentDto = new EmploymentDto(
                EmploymentStatus.UNEMPLOYED,
                "123456789012",
                BigDecimal.valueOf(50000),
                Position.MIDDLE_MANAGER,
                20,
                6
        );
    }


    @Test
    void checkEmploymentStatus() {
        assertThrows(RefuseException.class, employmentDto::checkEmploymentStatus);
        employmentDto.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        employmentDto.checkEmploymentStatus();
    }

    @Test
    void suitableExperience() {
        employmentDto.setWorkExperienceTotal(15);
        assertThrows(RefuseException.class, employmentDto::suitableExperience);
        employmentDto.setWorkExperienceTotal(20);
        employmentDto.suitableExperience();
        employmentDto.setWorkExperienceCurrent(2);
        assertThrows(RefuseException.class, employmentDto::suitableExperience);
    }
}