package dev.davletshin.statement.service.impl;

import dev.davletshin.statement.domain.exception.WebClientException;
import dev.davletshin.statement.service.DealClient;
import dev.davletshin.statement.web.dto.LoanOfferDto;
import dev.davletshin.statement.web.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealClientImpl implements DealClient {

    private static final String CREATE_OFFERS = "/deal/statement";
    private static final String SELECT_OFFER = "/deal/offer/select";
    private final WebClient webClient;

    @Override
    public List<LoanOfferDto> createOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return webClient.post()
                .uri(CREATE_OFFERS)
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
                .uri(SELECT_OFFER)
                .bodyValue(loanOfferDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }
}
