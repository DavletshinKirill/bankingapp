package dev.davletshin.deal.service.impl;

import dev.davletshin.calculator.web.dto.EmailMessageDTO;
import dev.davletshin.calculator.web.dto.Theme;
import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.client.Employment;
import dev.davletshin.deal.domain.client.Passport;
import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.domain.credit.CreditStatus;
import dev.davletshin.deal.domain.statement.ApplicationStatus;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.domain.statement.StatusHistory;
import dev.davletshin.deal.service.factory.EmailMessageFactory;
import dev.davletshin.deal.service.factory.PassportFactory;
import dev.davletshin.deal.service.factory.ScoringDataFactory;
import dev.davletshin.deal.service.factory.StatusHistoryFactory;
import dev.davletshin.deal.service.interfaces.*;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.mapper.CreditMapper;
import dev.davletshin.deal.web.mapper.EmploymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CreditService creditService;
    private final CalculatorClient sendRequestToCalculateService;
    private final PassportFactory passportFactory;
    private final StatusHistoryFactory statusHistoryFactory;
    private final ScoringDataFactory scoringDataFactory;
    private final EmploymentMapper employmentMapper;
    private final CreditMapper creditMapper;
    private final BrokerSender dossierSender;
    private final EmailMessageFactory emailMessageFactory;

    @Override
    public List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client) {
        Passport passport = passportFactory.createNewPassportWithNumberAndSeries(
                loanStatementRequestDto.getPassportSeries(),
                loanStatementRequestDto.getPassportNumber()
        );
        client.setPassport(passport);
        Client createdClient = clientService.createClient(client);
        Statement statement = Statement.builder()
                .client(createdClient)
                .creationDate(LocalDateTime.now())
                .build();

        statementService.saveStatement(statement);

        List<LoanOfferDto> loanOfferDtoList = sendRequestToCalculateService.postRequestToCalculateOffers(loanStatementRequestDto);
        loanOfferDtoList.forEach(loanOfferDto -> loanOfferDto.setStatementId(statement.getId()));
        return loanOfferDtoList;
    }

    @Override
    public Statement updateStatement(LoanOfferDto loanOfferDto) {
        Statement statement = statementService.getStatement(loanOfferDto.getStatementId());
        if (statement.getStatus() == null) statement.setStatus(ApplicationStatus.PREAPPROVAL);
        else statement.setStatus(statement.getStatus().next());

        statement.setAppliedOffer(loanOfferDto);

        List<StatusHistory> statusHistoryList = statement.getStatusHistory();
        if (statusHistoryList == null) statusHistoryList = new ArrayList<>();
        statusHistoryList.add(statusHistoryFactory.createStatusHistory(statement.getStatus()));
        statement.setStatusHistory(statusHistoryList);

        return statementService.saveStatement(statement);
    }

    @Override
    public Statement calculateCredit(UUID statementUUID, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        Statement statement = statementService.getStatement(statementUUID);
        Client client = statement.getClient();
        ScoringDataDto scoringDataDto = scoringDataFactory.createScoringData(statement, client, finishRegistrationRequestDto);
        EmailMessageDTO emailMessageDTO = emailMessageFactory.createEmailMessage(client, statementUUID, Theme.FINISH_REGISTRATION, null);
        dossierSender.send(emailMessageDTO, Theme.FINISH_REGISTRATION);
        Passport passport = passportFactory.fillIssueBranchAndDate(
                client.getPassport(),
                finishRegistrationRequestDto.getPassportIssueBranch(),
                finishRegistrationRequestDto.getPassportIssueDate()
        );

        Employment employment = employmentMapper.toEntity(finishRegistrationRequestDto.getEmployment());
        employment.setId(UUID.randomUUID());

        client.setPassport(passport);
        client.setEmployment(employment);

        Credit credit = creditMapper.toEntity(sendRequestToCalculateService.postRequestToCalculateCredit(scoringDataDto));
        credit.setCreditStatus(CreditStatus.CALCULATED);
        Credit savedCredit = creditService.createCredit(credit);
        Client savedClient = clientService.createClient(fillClient(client, finishRegistrationRequestDto));
        statement.setCredit(savedCredit);
        statement.setClient(savedClient);
        return statementService.saveStatement(statement);
    }

    private Client fillClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        client.setDependentAmount(finishRegistrationRequestDto.getDependentAmount());
        client.setGender(finishRegistrationRequestDto.getGender());
        client.setMaritalStatus(finishRegistrationRequestDto.getMaritalStatus());
        client.setAccountNumber(finishRegistrationRequestDto.getAccountNumber());
        return client;
    }
}
