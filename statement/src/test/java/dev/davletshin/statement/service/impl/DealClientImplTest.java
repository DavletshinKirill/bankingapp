package dev.davletshin.statement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealClientImplTest {

    private DealClientImpl dealClient;

    @Mock
    private ExchangeFunction exchangeFunction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOffers() {
        List<LoanOfferDto> loanOffers = List.of(new LoanOfferDto(), new LoanOfferDto());
        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
        dealClient = new DealClientImpl(webClient);
        when(exchangeFunction.exchange(any()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("content-type", "application/json")
                        .body(convertToJson(loanOffers))
                        .build()));
        List<LoanOfferDto> result = dealClient.createOffers(new LoanStatementRequestDto());
        ArgumentCaptor<ClientRequest> requestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
        verify(exchangeFunction).exchange(requestCaptor.capture());
        assertNotNull(result);
        assertEquals(loanOffers, result);
        assertEquals(loanOffers.size(), result.size());
    }

    @Test
    void updateOffer() {
        WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
        dealClient = new DealClientImpl(webClient);
        when(exchangeFunction.exchange(any()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("content-type", "application/json")
                        .body(convertToJson())
                        .build()));
        dealClient.updateOffer(new LoanOfferDto());
        ArgumentCaptor<ClientRequest> requestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
        verify(exchangeFunction).exchange(requestCaptor.capture());
    }

    private String convertToJson(List<LoanOfferDto> loanOffers) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(loanOffers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]"; // Return an empty JSON array in case of error
        }
    }

    private String convertToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString("");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]"; // Return an empty JSON array in case of error
        }
    }
}