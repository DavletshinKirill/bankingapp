package dev.davletshin.deal.service.factory;

import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.mapper.ClientToLoanStatementRequestMapper;
import dev.davletshin.deal.web.mapper.FinishRegistrationRequestToScoringDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ScoringDataFactory {

    private final FinishRegistrationRequestToScoringDataMapper finishRegistrationRequestToScoringDataMapper;
    private final ClientToLoanStatementRequestMapper clientToLoanStatementRequestMapper;

    public ScoringDataDto createScoringData(Statement statement, Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {

        BigDecimal amount = statement.getAppliedOffer().getRequestedAmount();
        int term = statement.getAppliedOffer().getTerm();
        boolean isSalaryClient = statement.getAppliedOffer().isSalaryClient();
        boolean isInsuranceEnabled = statement.getAppliedOffer().isInsuranceEnabled();

        LoanStatementRequestDto loanStatementRequestDto = clientToLoanStatementRequestMapper.toLoanStatementRequestDto(client, amount, term);
        ScoringDataDto scoringDataDto = finishRegistrationRequestToScoringDataMapper.toScoringDataDto(finishRegistrationRequestDto, isInsuranceEnabled, isSalaryClient);

        scoringDataDto.setLoanStatementRequestDto(loanStatementRequestDto);
        return scoringDataDto;
    }
}
