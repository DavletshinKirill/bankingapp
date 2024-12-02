package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.client.Employment;
import dev.davletshin.deal.domain.client.Passport;
import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.domain.credit.CreditStatus;
import dev.davletshin.deal.domain.statement.ApplicationStatus;
import dev.davletshin.deal.domain.statement.ChangeType;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.domain.statement.StatusHistory;
import dev.davletshin.deal.service.interfaces.ClientService;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.*;
import dev.davletshin.deal.web.mapper.CreditMapper;
import dev.davletshin.deal.web.mapper.EmploymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private static final String ENDPOINT_OFFERS = "/calculator/offers";
    private static final String ENDPOINT_CALCULATOR = "/calculator/calc";

    private final ClientService clientService;
    private final StatementService statementService;
    private final WebClient webClient;
    private final EmploymentMapper employmentMapper;
    private final CreditMapper creditMapper;

    @Override
    public List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client) {
        Client createdClient = clientService.create(client);
        Statement statement = statementService.createStatement(createdClient);

        List<LoanOfferDto> loanOfferDtoList = postRequestToCalculateOffers(loanStatementRequestDto);
        loanOfferDtoList.forEach(loanOfferDto -> loanOfferDto.setStatementId(statement.getId()));
        return loanOfferDtoList;
    }

    @Override
    public void updateStatement(LoanOfferDto loanOfferDto) {
        Statement statement = statementService.getStatement(loanOfferDto.getStatementId());
        if (statement.getStatus() == null) statement.setStatus(ApplicationStatus.PREAPPROVAL);
        else statement.setStatus(statement.getStatus().next());

        List<LoanOfferDto> loanOfferDtoList = statement.getAppliedOffer();
        if (loanOfferDtoList == null) loanOfferDtoList = new ArrayList<>();
        loanOfferDtoList.add(loanOfferDto);
        statement.setAppliedOffer(loanOfferDtoList);

        List<StatusHistory> statusHistoryList = statement.getStatusHistory();
        if (statusHistoryList == null) statusHistoryList = new ArrayList<>();
        statusHistoryList.add(createStatusHistory(statement.getStatus()));
        statement.setStatusHistory(statusHistoryList);

        statementService.saveStatement(statement);
    }

    @Override
    public void calculateCredit(String statementUUID, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Statement statement = statementService.getStatement(UUID.fromString(statementUUID));
        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .gender(finishRegistrationRequestDto.getGender())
                .maritalStatus(finishRegistrationRequestDto.getMaritalStatus())
                .dependentAmount(finishRegistrationRequestDto.getDependentAmount())
                .passportIssueDate(finishRegistrationRequestDto.getPassportIssueDate())
                .passportIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch())
                .employment(finishRegistrationRequestDto.getEmployment())
                .accountNumber(finishRegistrationRequestDto.getAccountNumber())
                .build();

        Passport passport = Passport.builder()
                .issueBranch(finishRegistrationRequestDto.getPassportIssueBranch())
                .issueDate(finishRegistrationRequestDto.getPassportIssueDate())
                .build();
        Employment employment = employmentMapper.toEntity(finishRegistrationRequestDto.getEmployment());
        Client client = Client.builder()
                .gender(finishRegistrationRequestDto.getGender())
                .maritalStatus(finishRegistrationRequestDto.getMaritalStatus())
                .dependentAmount(finishRegistrationRequestDto.getDependentAmount())
                .passport(passport)
                .employment(employment)
                .accountNumber(finishRegistrationRequestDto.getAccountNumber())
                .build();
        Credit credit = creditMapper.toEntity(postRequestToCalculateCredit(scoringDataDto));
        credit.setCreditStatus(CreditStatus.CALCULATED);
        statement.setClient(client);
        statement.setCredit(credit);
        statementService.saveStatement(statement);
    }

    private List<LoanOfferDto> postRequestToCalculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return webClient.post()
                .uri(ENDPOINT_OFFERS)
                .bodyValue(loanStatementRequestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                })
                .doOnError(error -> log.error(error.getMessage())).block();
    }

    private CreditDto postRequestToCalculateCredit(ScoringDataDto scoringDataDto) {
        return webClient.post()
                .uri(ENDPOINT_CALCULATOR)
                .bodyValue(scoringDataDto)
                .retrieve()
                .bodyToMono(CreditDto.class)
                .doOnError(error -> log.error(error.getMessage())).block();
    }

    private StatusHistory createStatusHistory(ApplicationStatus applicationStatus) {
        return StatusHistory.builder()
                .status(applicationStatus.toString())
                .changeType(ChangeType.AUTOMATIC)
                .time(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
