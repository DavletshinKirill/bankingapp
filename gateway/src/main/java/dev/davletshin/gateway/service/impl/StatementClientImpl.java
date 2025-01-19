package dev.davletshin.gateway.service.impl;

import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.gateway.domain.WebClientException;
import dev.davletshin.gateway.service.StatementClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementClientImpl implements StatementClient {
    private static final String CREATE_OFFERS = "/deal/statement";
    private static final String SELECT_OFFER = "/deal/offer/select";
    private final WebClient webClient;

    @Value("${http.urls.statement}")
    private String statementUrl;

    @Override
    public List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return webClient.post()
                .uri(statementUrl + CREATE_OFFERS)
                .bodyValue(loanStatementRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                })
                .doOnError(error -> log.error(error.getMessage())
                ).block();
    }

    @Override
    public void updateOffer(LoanOfferDto loanOfferDto) {
        webClient.post()
                .uri(statementUrl + SELECT_OFFER)
                .bodyValue(loanOfferDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }
}
