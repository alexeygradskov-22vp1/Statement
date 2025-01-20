package ru.gav.creditbank.statement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;
import ru.gav.creditbank.statement.service.StatementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final String dealStatementEndpoint = "deal/statement";
    private final String selectOfferEndpoint = "deal/offer/select";

    private final WebClient dealWebClient;

    @Override
    public List<LoanOfferDto> score(LoanStatementRequestDto loanStatementRequestDto) {
        return dealWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(dealStatementEndpoint).build())
                .body(BodyInserters.fromValue(loanStatementRequestDto))
                .retrieve()
                .bodyToFlux(LoanOfferDto.class)
                .toStream().toList();
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        dealWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(selectOfferEndpoint).build())
                .body(BodyInserters.fromValue(loanOfferDto))
                .retrieve().toBodilessEntity().block();
    }
}
