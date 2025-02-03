package dev.davletshin.deal.web.mapper;


import dev.davletshin.deal.web.dto.FinishRegistrationRequestDto;
import dev.davletshin.deal.web.dto.ScoringDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinishRegistrationRequestToScoringDataMapper {

    @Mapping(target = "isInsuranceEnabled", expression = "java(isInsuranceEnabled)")
    @Mapping(target = "isSalaryClient", expression = "java(isSalaryClient)")
    ScoringDataDto toScoringDataDto(FinishRegistrationRequestDto finishRegistrationRequestDto, boolean isInsuranceEnabled, boolean isSalaryClient);
}
