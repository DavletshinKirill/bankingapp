package dev.davletshin.deal.web.mapper;

import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.domain.client.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ClientToLoanStatementRequestMapper {

    @Mapping(target = "amount", expression = "java(amount)")
    @Mapping(target = "term", expression = "java(term)")
    @Mapping(target = "passportSeries", expression = "java(client.getPassport().getSeries())")
    @Mapping(target = "passportNumber", expression = "java(client.getPassport().getNumber())")
    LoanStatementRequestDto toLoanStatementRequestDto(Client client, BigDecimal amount, int term);
}
