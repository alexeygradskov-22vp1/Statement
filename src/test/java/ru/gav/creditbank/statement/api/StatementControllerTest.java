package ru.gav.creditbank.statement.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;
import ru.gav.creditbank.statement.controller.StatementController;
import ru.gav.creditbank.statement.service.StatementService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class StatementControllerTest {

    @Mock
    private StatementService statementService;

    private StatementController statementController;

    private LoanOfferDto loanOfferDto;
    private LoanStatementRequestDto loanStatementRequestDto;
    private List<LoanOfferDto> list;

    @BeforeAll
    public void init() {
        try (InputStream loanStatementRequestDtoStream = LoanStatementRequestDto.class.getResourceAsStream("/data/loan-statement-request-dto.json");
             InputStream loanOfferDtoStream = LoanOfferDto.class.getResourceAsStream("/data/loan-offer-dto.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            loanOfferDto = objectMapper.readValue(loanOfferDtoStream, LoanOfferDto.class);
            loanStatementRequestDto = objectMapper.readValue(loanStatementRequestDtoStream, LoanStatementRequestDto.class);
            list = List.of(loanOfferDto, loanOfferDto, loanOfferDto, loanOfferDto);
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
    }

    @Test
    public void choosingOfferTest() {
        statementController = new StatementController(statementService);
        doNothing().when(statementService).selectOffer(loanOfferDto);
        statementController.choosingOffer(loanOfferDto);
    }

    @Test
    public void firstValidationApplicationTest() {
        statementController = new StatementController(statementService);
        doReturn(list).when(statementService).score(loanStatementRequestDto);
        List<LoanOfferDto> result = statementController.firstValidationApplication(loanStatementRequestDto).getBody();
        assertEquals(4, result.size());
        assertEquals(list, result);
    }
}
