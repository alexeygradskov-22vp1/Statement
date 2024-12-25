package ru.gav.creditbank.statement.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.gav.__model_.model.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestLoanOfferDto {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void testAllValidData(){
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("21000"));
        loanStatementRequestDto.setTerm(6);
        loanStatementRequestDto.setFirstName("Alex");
        loanStatementRequestDto.setLastName("Gradskov");
        loanStatementRequestDto.setMiddleName("Vladimirovich");
        loanStatementRequestDto.setEmail("gradskovaleksej@gmail.com");
        loanStatementRequestDto.setBirthdate(LocalDate.now().minusYears(20));
        loanStatementRequestDto.setPassportSeries("1234");
        loanStatementRequestDto.setPassportNumber("123456");
        Set<ConstraintViolation<LoanStatementRequestDto>> violations = validator.validate(loanStatementRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidBirthdateData(){
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("21000"));
        loanStatementRequestDto.setTerm(6);
        loanStatementRequestDto.setFirstName("Alex");
        loanStatementRequestDto.setLastName("Gradskov");
        loanStatementRequestDto.setMiddleName("Vladimirovich");
        loanStatementRequestDto.setEmail("gradskovaleksej@gmail.com");
        loanStatementRequestDto.setBirthdate(null);
        loanStatementRequestDto.setPassportSeries("1234");
        loanStatementRequestDto.setPassportNumber("123456");
        assertThrows(ValidationException.class,()->validator.validate(loanStatementRequestDto));
    }

    @Test
    public void testInvalidNullData(){
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(null);
        loanStatementRequestDto.setTerm(null);
        loanStatementRequestDto.setFirstName(null);
        loanStatementRequestDto.setLastName(null);
        loanStatementRequestDto.setMiddleName(null);
        loanStatementRequestDto.setEmail(null);
        loanStatementRequestDto.setBirthdate(LocalDate.now().minusYears(20));
        loanStatementRequestDto.setPassportSeries(null);
        loanStatementRequestDto.setPassportNumber(null);
        Set<ConstraintViolation<LoanStatementRequestDto>> violations = validator.validate(loanStatementRequestDto);
        assertEquals(7,violations.size());    }

}
