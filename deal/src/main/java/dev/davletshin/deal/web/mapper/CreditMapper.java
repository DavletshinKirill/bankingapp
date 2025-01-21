package dev.davletshin.deal.web.mapper;

import dev.davletshin.calculator.web.dto.credit.CreditDto;
import dev.davletshin.deal.domain.credit.Credit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper extends Mappable<Credit, CreditDto> {
}
