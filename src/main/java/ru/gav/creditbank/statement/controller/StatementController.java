package ru.gav.creditbank.statement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.gav.__model_.StatementsApi;
import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;

@RestController
@RequiredArgsConstructor
public class StatementController implements StatementsApi {
    @Override
    public ResponseEntity<Void> choosingOffer(LoanOfferDto loanOfferDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> firstValidationApplication(LoanStatementRequestDto loanStatementRequestDto) {
        return null;
    }
}
