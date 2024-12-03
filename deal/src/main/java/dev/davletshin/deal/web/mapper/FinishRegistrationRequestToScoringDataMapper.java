package dev.davletshin.deal.web.mapper;

import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.dto.ScoringDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinishRegistrationRequestToScoringDataMapper {
    FinishRegistrationRequestDto toFinishRegistrationRequest(ScoringDataDto scoringDataDto);

    ScoringDataDto toScoringDataDto(FinishRegistrationRequestDto finishRegistrationRequestDto);
}
