package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.web.dto.CreditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper extends Mappable<Credit, CreditDto> {
}
