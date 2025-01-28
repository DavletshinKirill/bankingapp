package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.client.Employment;
import dev.davletshin.deal.web.dto.EmploymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper extends Mappable<Employment, EmploymentDto> {
}
