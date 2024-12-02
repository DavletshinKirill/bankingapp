package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto, Client client);
}
