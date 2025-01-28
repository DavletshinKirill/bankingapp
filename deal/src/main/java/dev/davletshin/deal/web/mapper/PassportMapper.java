package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.client.Passport;
import dev.davletshin.deal.web.dto.PassportDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper extends Mappable<Passport, PassportDto> {
}
