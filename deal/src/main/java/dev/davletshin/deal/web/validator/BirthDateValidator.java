package dev.davletshin.deal.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<ValidateBirthDate, LocalDate> {
    @Override
    public void initialize(ValidateBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate dateBirth, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return eighteenYearsAgo.isAfter(dateBirth);
    }
}
