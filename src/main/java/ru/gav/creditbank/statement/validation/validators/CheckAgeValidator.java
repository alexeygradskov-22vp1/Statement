package ru.gav.creditbank.statement.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.gav.creditbank.statement.validation.CheckAgeAnnotation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class CheckAgeValidator implements ConstraintValidator<CheckAgeAnnotation, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate birthdate = Optional.ofNullable(localDate).orElseThrow();
        LocalDate now = LocalDate.now();
        return ChronoUnit.YEARS.between(birthdate, now)>=18;
    }
}
