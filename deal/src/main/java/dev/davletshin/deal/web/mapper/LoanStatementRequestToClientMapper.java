package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LoanStatementRequestToClientMapper extends Mappable<Client, LoanStatementRequestDto> {
}
