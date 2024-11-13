package dev.davletshin.calculator.web.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class BirthDateValidator implements ConstraintValidator<ValidateBirthDate, LocalDateTime> {
    @Override
    public void initialize(ValidateBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime dateBirth, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime eighteenYearsAgo = LocalDateTime.now().minusYears(18);
        return eighteenYearsAgo.isAfter(dateBirth);
    }
}
