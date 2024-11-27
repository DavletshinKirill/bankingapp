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

    private static final int maxNumberSalary = 24;
    private static final int ageFrom = 20;
    private static final int ageTo = 65;
    private static final int ageWomanFrom = 32;
    private static final int ageWomanTo = 60;
    private static final int ageManFrom = 30;
    private static final int ageManTo = 55;
    private static final int generalExperience = 18;
    private static final int currentExperience = 3;


    @Override
    public void checkAmountSalary(ScoringDataDto scoringDataDto) {
        BigDecimal salary = scoringDataDto.getEmployment().getSalary();
        if (salary.multiply(BigDecimal.valueOf(maxNumberSalary)).compareTo(scoringDataDto.getAmount()) < 0) {
            throw new RefuseException("The loan amount is too large");
        }
    }

    @Override
    public void checkAge(ScoringDataDto scoringDataDto) {
        if (!isAgeSuitable(ageFrom, ageTo, scoringDataDto.getBirthdate())) throw new RefuseException("The Wrong Age");
    }

    @Override
    public int getIndexGender(ScoringDataDto scoringDataDto) {
        Gender gender = scoringDataDto.getGender();
        switch (gender) {
            case FEMALE -> {
                if (!isAgeSuitable(ageWomanFrom, ageWomanTo, scoringDataDto.getBirthdate())) return 0;
            }
            case MALE -> {
                if (!isAgeSuitable(ageManFrom, ageManTo, scoringDataDto.getBirthdate())) return 0;
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
        int amount_age = age.getYears();
        return (startAge <= amount_age) && (amount_age <= endAge);
    }

    private void checkEmploymentStatus(EmploymentDto employment) {
        if (employment.getEmploymentStatus() == EmploymentStatus.UNEMPLOYED)
            throw new RefuseException("Loans are not given to the unemployed");
    }

    private void suitableExperience(EmploymentDto employment) {
        if ((employment.getWorkExperienceTotal() < generalExperience) || (employment.getWorkExperienceCurrent() < currentExperience))
            throw new RefuseException("Unsuitable work experience");
    }
}
