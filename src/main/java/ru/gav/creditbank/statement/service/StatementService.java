package ru.gav.creditbank.statement.service;

import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> score(LoanStatementRequestDto loanStatementRequestDto);
    void selectOffer(LoanOfferDto loanOfferDto);
}
