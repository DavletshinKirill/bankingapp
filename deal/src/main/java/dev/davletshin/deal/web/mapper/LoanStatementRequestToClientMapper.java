package dev.davletshin.deal.web.mapper;

import dev.davletshin.calculator.web.dto.offer.LoanStatementRequestDto;
import dev.davletshin.deal.domain.client.Client;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LoanStatementRequestToClientMapper extends Mappable<Client, LoanStatementRequestDto> {
}
