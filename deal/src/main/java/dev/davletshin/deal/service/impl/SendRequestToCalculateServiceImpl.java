package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.service.interfaces.SendRequestToCalculateService;
import dev.davletshin.deal.web.dto.CreditDto;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import dev.davletshin.deal.web.dto.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendRequestToCalculateServiceImpl implements SendRequestToCalculateService {

    private static final String ENDPOINT_OFFERS = "/calculator/offers";
    private static final String ENDPOINT_CALCULATOR = "/calculator/calc";

    private final WebClient webClient;

    @Override
    public List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return webClient.post()
                .uri(ENDPOINT_OFFERS)
                .bodyValue(loanStatementRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                })
                .doOnError(error -> log.error(error.getMessage())).block();
    }

    @Override
    public CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto) {
        return webClient.post()
                .uri(ENDPOINT_CALCULATOR)
                .bodyValue(scoringDataDto)
                .retrieve()
                .bodyToMono(CreditDto.class)
                .doOnError(error -> log.error(error.getMessage())).block();
    }
}
