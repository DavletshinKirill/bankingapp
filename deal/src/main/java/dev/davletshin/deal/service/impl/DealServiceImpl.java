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
import dev.davletshin.deal.service.interfaces.CreditService;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.*;
import dev.davletshin.deal.web.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
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
    private final CreditService creditService;
    private final WebClient webClient;
    private final EmploymentMapper employmentMapper;
    private final CreditMapper creditMapper;
    private final FinishRegistrationRequestToClientMapper finishRegistrationRequestToClientMapper;
    private final FinishRegistrationRequestToScoringDataMapper finishRegistrationRequestToScoringDataMapper;
    private final ClientToLoanStatementRequestMapper clientToLoanStatementRequestMapper;

    @Override
    public List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client) {
        Passport passport = Passport.builder()
                .series(loanStatementRequestDto.getPassportSeries())
                .number(loanStatementRequestDto.getPassportNumber())
                .build();
        client.setPassport(passport);
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
        ScoringDataDto scoringDataDto = finishRegistrationRequestToScoringDataMapper.toScoringDataDto(finishRegistrationRequestDto);
        int sizeAppliedOffer = statement.getAppliedOffer().size();
        BigDecimal amount = statement.getAppliedOffer().get(sizeAppliedOffer - 1).getRequestedAmount();
        int term = statement.getAppliedOffer().get(sizeAppliedOffer - 1).getTerm();
        boolean isSalaryClient = statement.getAppliedOffer().get(sizeAppliedOffer - 1).isSalaryClient();
        boolean isInsuranceEnabled = statement.getAppliedOffer().get(sizeAppliedOffer - 1).isInsuranceEnabled();
        Client client = statement.getClient();
        LoanStatementRequestDto loanStatementRequestDto = clientToLoanStatementRequestMapper.toDTO(client);
        loanStatementRequestDto.setAmount(amount);
        loanStatementRequestDto.setTerm(term);
        loanStatementRequestDto.setPassportNumber(client.getPassport().getNumber());
        loanStatementRequestDto.setPassportSeries(client.getPassport().getSeries());
        scoringDataDto.setIsSalaryClient(isSalaryClient);
        scoringDataDto.setIsInsuranceEnabled(isInsuranceEnabled);
        scoringDataDto.setLoanStatementRequestDto(loanStatementRequestDto);
        Passport passport = client.getPassport();
        passport.setIssueDate(finishRegistrationRequestDto.getPassportIssueDate());
        passport.setIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch());

        Employment employment = employmentMapper.toEntity(finishRegistrationRequestDto.getEmployment());
        Client returned_client = finishRegistrationRequestToClientMapper.toEntity(finishRegistrationRequestDto);
        returned_client.setPassport(passport);
        returned_client.setEmployment(employment);

        Credit credit = creditMapper.toEntity(postRequestToCalculateCredit(scoringDataDto));
        credit.setCreditStatus(CreditStatus.CALCULATED);
        Credit savedCredit = creditService.createCredit(credit);
        Client savedClient = clientService.create(client);
        statement.setCredit(savedCredit);
        statement.setClient(savedClient);
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
