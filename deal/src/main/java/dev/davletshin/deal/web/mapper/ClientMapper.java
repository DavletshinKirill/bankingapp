package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.web.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmploymentMapper.class, PassportMapper.class})
public interface ClientMapper extends Mappable<Client, ClientDto> {
}
