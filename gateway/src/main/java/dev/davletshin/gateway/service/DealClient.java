package dev.davletshin.gateway.service;


import dev.davletshin.calculator.web.dto.FinishRegistrationRequestDto;

import java.util.List;
import java.util.UUID;

public interface DealClient {

    StatementDto getStatement(UUID statementId);

    List<StatementDto> getStatements();

    void sendDocs(UUID statementId, String finalUrl);

    void checkCode(UUID statementId, UUID sesCode, String finalUrl);

    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, UUID statementId);

}
