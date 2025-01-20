package ru.gav.creditbank.statement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gav.__model_.model.LoanOfferDto;
import ru.gav.__model_.model.LoanStatementRequestDto;
import ru.gav.creditbank.statement.service.impl.StatementServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class StatementServiceTest {
    @Mock
    private WebClient dealWebClientMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private Flux<LoanOfferDto> loanOfferDtoFlux;
    @Mock
    private Stream<LoanOfferDto> loanOfferDtoStream;
    @Mock
    private List<LoanOfferDto> list;
    Mono<ResponseEntity<Void>> mono = Mono.just(ResponseEntity.ok().build());

    @InjectMocks
    private StatementServiceImpl statementService;

    private LoanOfferDto loanOfferDto;
    private LoanStatementRequestDto loanStatementRequestDto;

    @BeforeAll
    public void init(){
        try (InputStream loanStatementRequestDtoStream = LoanStatementRequestDto.class.getResourceAsStream("/data/loan-statement-request-dto.json");
             InputStream loanOfferDtoStream = LoanOfferDto.class.getResourceAsStream("/data/loan-offer-dto.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            loanOfferDto = objectMapper.readValue(loanOfferDtoStream, LoanOfferDto.class);
            loanStatementRequestDto = objectMapper.readValue(loanStatementRequestDtoStream, LoanStatementRequestDto.class);
            list = List.of(loanOfferDto, loanOfferDto, loanOfferDto, loanOfferDto);
        }catch (IOException ioException){
            log.error("e: ", ioException);
        }
    }

    @Test
    public void scoreTest(){
        doReturn(requestBodyUriSpec).when(dealWebClientMock).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(any(Function.class));
        doReturn(requestHeadersSpec).when(requestBodySpec).body(any(BodyInserter.class));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(loanOfferDtoFlux).when(responseSpec).bodyToFlux(LoanOfferDto.class);
        doReturn(loanOfferDtoStream).when(loanOfferDtoFlux).toStream();
        doReturn(list).when(loanOfferDtoStream).toList();
        Assertions.assertEquals(list, statementService.score(loanStatementRequestDto));
    }


}
