package dev.davletshin.calculator.service;

import dev.davletshin.calculator.web.dto.credit.ScoringDataDto;

public interface ScoringService {
    void checkAmountSalary(ScoringDataDto scoringDataDto);

    void checkAge(ScoringDataDto scoringDataDto);

    int getIndexGender(ScoringDataDto scoringDataDto);

    int getIndexEmployment(ScoringDataDto scoringDataDto);
}
