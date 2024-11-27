package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.config.TestConfig;
import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.Position;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.calculator.service.ScoringService;
import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
public class ScoringServiceImplTest {

    @Mock
    private ScoringDataDto scoringDataDto;

    @Mock
    private EmploymentDto employment;

    @Autowired
    private ScoringService scoringService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkAmountSalaryDoesNotThrowRefuseException() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);
        when(employment.getSalary()).thenReturn(new BigDecimal("10000"));
        when(scoringDataDto.getAmount()).thenReturn(new BigDecimal("20000"));
        assertDoesNotThrow(() -> {
            scoringService.checkAmountSalary(scoringDataDto);
        });
    }

    @Test
    void checkAmountSalaryThrowRefuseException() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);
        when(employment.getSalary()).thenReturn(new BigDecimal("10000"));
        when(scoringDataDto.getAmount()).thenReturn(new BigDecimal("400000"));
        Exception exception = assertThrows(RefuseException.class, () -> {
            scoringService.checkAmountSalary(scoringDataDto);
        });
        assertEquals("The loan amount is too large", exception.getMessage());
    }

    @Test
    void checkAgeDoesNotThrowRefuseException() {
        when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(1990, 1, 1));
        assertDoesNotThrow(() -> {
            scoringService.checkAge(scoringDataDto);
        });
    }

    @Test
    void checkAgeThrowRefuseException() {
        when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(2010, 1, 1));
        Exception exception = assertThrows(RefuseException.class, () -> {
            scoringService.checkAge(scoringDataDto);
        });
        assertEquals("The Wrong Age", exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(Gender.class)
    void getIndexGender(Gender gender) {
        when(scoringDataDto.getGender()).thenReturn(gender);
        lenient().when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(1990, 1, 1));
        assertEquals(gender.getIndexGender(), scoringService.getIndexGender(scoringDataDto));
    }

    @ParameterizedTest
    @MethodSource("provideGenders")
    void getIndexGenderReturnZero(Gender gender) {
        when(scoringDataDto.getGender()).thenReturn(gender);
        when(scoringDataDto.getBirthdate()).thenReturn(LocalDate.of(2000, 1, 1));
        assertEquals(0, scoringService.getIndexGender(scoringDataDto));
    }

    @Test
    void getIndexEmployment() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);
        when(employment.getPosition()).thenReturn(Position.MIDDLE_MANAGER);
        when(employment.getEmploymentStatus()).thenReturn(EmploymentStatus.BUSINESS_OWNER);
        when(employment.getWorkExperienceTotal()).thenReturn(20);
        when(employment.getWorkExperienceCurrent()).thenReturn(5);
        assertEquals(employment.getPosition().getIndexEmployment()
                + employment.getEmploymentStatus().getIndexEmployment(), scoringService.getIndexEmployment(scoringDataDto));
    }

    @Test
    void getIndexEmploymentThrowRefuseExceptionSuitableExperienceByIncorrectTotalExperience() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);

        when(employment.getWorkExperienceTotal()).thenReturn(15);
        Exception exception = assertThrows(RefuseException.class, () -> {
            scoringService.getIndexEmployment(scoringDataDto);
        });
        assertEquals("Unsuitable work experience", exception.getMessage());
    }

    @Test
    void getIndexEmploymentThrowRefuseExceptionSuitableExperienceByIncorrectCurrentExperience() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);

        when(employment.getWorkExperienceTotal()).thenReturn(20);
        when(employment.getWorkExperienceCurrent()).thenReturn(1);

        Exception exception = assertThrows(RefuseException.class, () -> {
            scoringService.getIndexEmployment(scoringDataDto);
        });
        assertEquals("Unsuitable work experience", exception.getMessage());
    }

    @Test
    void getIndexEmploymentThrowRefuseExceptionByEmploymentStatus() {
        when(scoringDataDto.getEmployment()).thenReturn(employment);
        when(employment.getEmploymentStatus()).thenReturn(EmploymentStatus.UNEMPLOYED);
        when(employment.getWorkExperienceTotal()).thenReturn(20);
        when(employment.getWorkExperienceCurrent()).thenReturn(5);
        Exception exception = assertThrows(RefuseException.class, () -> {
            scoringService.getIndexEmployment(scoringDataDto);
        });
        assertEquals("Loans are not given to the unemployed", exception.getMessage());
    }

    static Stream<Gender> provideGenders() {
        return Stream.of(Gender.MALE, Gender.FEMALE);
    }
}
