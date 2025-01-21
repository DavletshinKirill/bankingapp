package dev.davletshin.gateway.service.impl;

import dev.davletshin.calculator.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.gateway.domain.WebClientException;
import dev.davletshin.gateway.service.DealClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealClientImpl implements DealClient {

    private static final String GET_STATEMENT = "/deal/admin/statement";
    private static final String DEAL_DOCUMENT = "/deal/document";
    private static final String CALCULATE_CREDIT = "/deal/calculate/";
    private final WebClient webClient;

    @Value("${http.urls.deal}")
    private String dealUrl;

//    @Override
//    public StatementDto getStatement(UUID statementId) {
//        return webClient.get()
//                .uri(dealUrl + GET_STATEMENT + "/" + statementId.toString())
//                .retrieve()
//                .onStatus(
//                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
//                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
//                .bodyToMono(StatementDto.class)
//                .block();
//    }
//
//    @Override
//    public List<StatementDto> getStatements() {
//        return webClient.get()
//                .uri(dealUrl + GET_STATEMENT)
//                .retrieve()
//                .onStatus(
//                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
//                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
//                .bodyToMono(new ParameterizedTypeReference<List<StatementDto>>() {
//                })
//                .block();
//    }

    @Override
    public void sendDocs(UUID statementId, String finalUrl) {
        webClient.post()
                .uri(dealUrl + DEAL_DOCUMENT + finalUrl)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }

    @Override
    public void checkCode(UUID statementId, UUID sesCode, String finalUrl) {
        webClient.post()
                .uri(dealUrl + DEAL_DOCUMENT + finalUrl)
                .bodyValue(sesCode)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }

    @Override
    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, UUID statementId) {
        webClient.post()
                .uri(dealUrl + CALCULATE_CREDIT + statementId.toString())
                .bodyValue(finishRegistrationRequestDto)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .flatMap(ex -> Mono.error(new WebClientException(ex.getMessage(), clientResponse.statusCode()))))
                .bodyToMono(void.class)
                .block();
    }
}
