package dev.davletshin.gateway.service.impl;

import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.gateway.domain.WebClientException;
import dev.davletshin.gateway.service.StatementClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementClientImpl implements StatementClient {

    private final WebClient webClient;

    @Override
    public void updateOffer(LoanOfferDto loanOfferDto, String baseUrl, String finalUrl) {
        webClient.post()
                .uri(baseUrl + finalUrl)
                .bodyValue(loanOfferDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }
}
