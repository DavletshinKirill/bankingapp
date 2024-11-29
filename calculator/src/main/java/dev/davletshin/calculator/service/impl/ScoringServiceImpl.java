package dev.davletshin.calculator.service.impl;

import dev.davletshin.calculator.domain.EmploymentStatus;
import dev.davletshin.calculator.domain.Gender;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.calculator.service.ScoringService;
import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ScoringServiceImpl implements ScoringService {

    private static final int MAX_NUMBER_SALARY = 24;
    private static final int AGE_FROM = 20;
    private static final int AGE_TO = 65;
    private static final int AGE_WOMAN_FROM = 32;
    private static final int AGE_WOMAN_TO = 60;
    private static final int AGE_MAN_FROM = 30;
    private static final int AGE_MAN_TO = 55;
    private static final int GENERAL_EXPERIENCE = 18;
    private static final int CURRENT_EXPERIENCE = 3;


    @Override
    public void checkAmountSalary(ScoringDataDto scoringDataDto) {
        BigDecimal salary = scoringDataDto.getEmployment().getSalary();
        if (salary.multiply(BigDecimal.valueOf(MAX_NUMBER_SALARY)).compareTo(scoringDataDto.getAmount()) < 0) {
            throw new RefuseException("The loan amount is too large");
        }
    }

    @Override
    public void checkAge(ScoringDataDto scoringDataDto) {
        if (isAgeSuitable(AGE_FROM, AGE_TO, scoringDataDto.getBirthdate())) throw new RefuseException("The Wrong Age");
    }

    @Override
    public int getIndexGender(ScoringDataDto scoringDataDto) {
        Gender gender = scoringDataDto.getGender();
        switch (gender) {
            case FEMALE -> {
                if (isAgeSuitable(AGE_WOMAN_FROM, AGE_WOMAN_TO, scoringDataDto.getBirthdate())) return 0;
            }
            case MALE -> {
                if (isAgeSuitable(AGE_MAN_FROM, AGE_MAN_TO, scoringDataDto.getBirthdate())) return 0;
            }
        }
        return gender.getIndexGender();
    }

    @Override
    public int getIndexEmployment(ScoringDataDto scoringDataDto) {
        EmploymentDto employment = scoringDataDto.getEmployment();
        suitableExperience(employment);
        checkEmploymentStatus(employment);
        return employment.getPosition().getIndexEmployment() + employment.getEmploymentStatus().getIndexEmployment();
    }

    private boolean isAgeSuitable(int startAge, int endAge, LocalDate date) {
        Period age = Period.between(date, LocalDate.now());
        int amountAge = age.getYears();
        return (startAge > amountAge) || (amountAge > endAge);
    }

    private void checkEmploymentStatus(EmploymentDto employment) {
        if (employment.getEmploymentStatus() == EmploymentStatus.UNEMPLOYED)
            throw new RefuseException("Loans are not given to the unemployed");
    }

    private void suitableExperience(EmploymentDto employment) {
        if ((employment.getWorkExperienceTotal() < GENERAL_EXPERIENCE) || (employment.getWorkExperienceCurrent() < CURRENT_EXPERIENCE))
            throw new RefuseException("Unsuitable work experience");
    }
}
