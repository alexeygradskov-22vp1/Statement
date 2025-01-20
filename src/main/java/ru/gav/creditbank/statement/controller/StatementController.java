package ru.gav.creditbank.statement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.gav.__model_.StatementsApi;
import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;
import ru.gav.creditbank.statement.service.StatementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatementController implements StatementsApi {
    private final StatementService statementService;
    @Override
    public ResponseEntity<Void> choosingOffer(LoanOfferDto loanOfferDto) {
        statementService.selectOffer(loanOfferDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<LoanOfferDto>> firstValidationApplication(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> result = statementService.score(loanStatementRequestDto);
        return ResponseEntity.ok(result);
    }
}
