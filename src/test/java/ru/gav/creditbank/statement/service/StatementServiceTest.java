package ru.gav.creditbank.statement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatementServiceTest {
    @Mock
    private WebClient dealWebClientMock;

    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    private WebClient.RequestBodySpec requestBodySpec;
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    private WebClient.ResponseSpec responseSpec;
    private Flux<LoanOfferDto> loanOfferDtoFlux;
    private Stream<LoanOfferDto> loanOfferDtoStream;
    private List<LoanOfferDto> list;


    @InjectMocks
    private StatementServiceImpl statementService;

    private LoanOfferDto loanOfferDto;
    private LoanStatementRequestDto loanStatementRequestDto;

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
            log.error("e: ", ioException);
        }
    }

    @Test
    @Order(1)
    public void scoreTest() {
        mockWebClient();
        doReturn(requestBodyUriSpec).when(dealWebClientMock).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(any(Function.class));
        doReturn(requestHeadersSpec).when(requestBodySpec).body(any(BodyInserter.class));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(Flux.fromIterable(list)).when(responseSpec).bodyToFlux(LoanOfferDto.class);
        Assertions.assertEquals(list, statementService.score(loanStatementRequestDto));
    }

    private void mockWebClient() {
        requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        requestBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        responseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        loanOfferDtoFlux = Mockito.mock(Flux.class);
        loanOfferDtoStream = Mockito.mock(Stream.class);
    }

    @Test
    @Order(2)
    public void selectOfferTest() {
        Mono<ResponseEntity<Void>> mono = Mono.just(ResponseEntity.ok().build());
        mockWebClient();
        doReturn(requestBodyUriSpec).when(dealWebClientMock).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(any(Function.class));
        doReturn(requestHeadersSpec).when(requestBodySpec).body(any(BodyInserter.class));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
        doReturn(mono).when(responseSpec).toBodilessEntity();
    }


}
