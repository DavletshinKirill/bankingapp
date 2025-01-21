package dev.davletshin.deal.web.mapper;

import dev.davletshin.calculator.web.dto.credit.EmploymentDto;
import dev.davletshin.deal.domain.client.Employment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper extends Mappable<Employment, EmploymentDto> {
}
