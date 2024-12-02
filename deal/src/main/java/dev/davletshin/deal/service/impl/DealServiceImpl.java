package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.interfaces.ClientService;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private static final String ENDPOINT = "/calculator/offers";

    private final ClientService clientService;
    private final StatementService statementService;
    private final WebClient webClient;

    @Override
    public List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client) {
        Client createdClient = clientService.create(client);
        Statement statement = statementService.createStatement(createdClient);

        List<LoanOfferDto> loanOfferDtos = postRequestToCalculator(loanStatementRequestDto);
        loanOfferDtos.forEach(loanOfferDto -> loanOfferDto.setStatementId(statement.getId()));
        return loanOfferDtos;
    }

    private List<LoanOfferDto> postRequestToCalculator(LoanStatementRequestDto loanStatementRequestDto) {
        return webClient.post()
                .uri(ENDPOINT)
                .bodyValue(loanStatementRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                })
                .doOnError(error -> log.error(error.getMessage())).block();
    }
}
