package dev.davletshin.deal.service.interfaces;

import dev.davletshin.calculator.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.calculator.web.dto.offer.LoanOfferDto;
import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.domain.statement.Statement;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client);

    Statement updateStatement(LoanOfferDto loanOfferDto);

    Statement calculateCredit(UUID statementUUID, FinishRegistrationRequestDto finishRegistrationRequestDto);
}
