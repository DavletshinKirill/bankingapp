package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinishRegistrationRequestToClientMapper extends Mappable<Client, FinishRegistrationRequestDto> {
}
