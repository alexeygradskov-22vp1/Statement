package ru.gav.creditbank.statement.validation.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import ru.gav.creditbank.statement.validation.CheckAgeAnnotation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckAgeValidatorTest {
    private CheckAgeValidator checkAgeValidator;
    @Mock
    private ConstraintValidatorContext context;

    @BeforeAll
    public void init() {
        checkAgeValidator = new CheckAgeValidator();
    }

    @Test
    void isValidTest() {
        LocalDate now = LocalDate.now();
        LocalDate twentyYears = now.minusYears(20);
        assertTrue(checkAgeValidator.isValid(twentyYears, context));
    }

    @Test
    void isNotValidTest(){
        LocalDate now = LocalDate.now();
        LocalDate birthdate = now.minusDays(1);
        assertFalse(checkAgeValidator.isValid(birthdate, context));
    }
}