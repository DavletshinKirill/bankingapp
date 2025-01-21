package dev.davletshin.gateway.service.impl;

import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.gateway.service.CalculatorClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorClientImpl implements CalculatorClient {
    private static final String ENDPOINT_CALCULATOR = "/calculator/calc";

    @Value("${http.urls.calculator}")
    private String calculatorUrl;

    private final WebClient webClient;

    @Override
    public List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto, String baseUrl, String finalUrl) {
        return webClient.post()
                .uri(baseUrl + finalUrl)
                .bodyValue(loanStatementRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                })
                .doOnError(error -> log.error(error.getMessage())).block();
    }

    @Override
    public CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto, String baseUrl) {
        return webClient.post()
                .uri(baseUrl + ENDPOINT_CALCULATOR)
                .bodyValue(scoringDataDto)
                .retrieve()
                .bodyToMono(CreditDto.class)
                .doOnError(error -> log.error(error.getMessage())).block();
    }
}
