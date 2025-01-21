package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.web.dto.StatementDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, CreditMapper.class})
public interface StatementMapper extends Mappable<Statement, StatementDto> {
    List<Statement> toEntity(List<StatementDto> statementDtos);

    List<StatementDto> toDTO(List<Statement> statements);

}
