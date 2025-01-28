package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.domain.enums.ApplicationStatus;
import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.StatementDto;
import dev.davletshin.deal.web.mapper.StatementMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin API")
@Slf4j
public class AdminController {

    private final StatementService statementService;

    @Operation(summary = "getStatementById", description = "Get Statement By Id")
    @GetMapping("/statement/{statementId}")
    public StatementDto getStatementById(@PathVariable UUID statementId) {
        log.info("Get Statement By Id: {}", statementId);
        StatementDto statementDto = statementService.getStatementById(statementId);
        log.info("Get Statement By Id: {}", statementDto.toString());
        return statementDto;
    }

    @Operation(summary = "getAllStatements", description = "Get All Statements")
    @GetMapping("/statement")
    public List<StatementDto> getAllStatements() {
        log.info("Get All Statements");
        List<StatementDto> statementDtoList = statementService.getStatements();
        log.info("Get All Statements: {}", statementDtoList.toString());
        return statementDtoList;
    }

    @Operation(summary = "updateStatementStatus", description = "Update Statement status")
    @PutMapping("/statement/{statementId}/status")
    public void updateStatementStatus(@PathVariable UUID statementId, @RequestBody ApplicationStatus status) {
        log.info("Update Statement By Id: {}, Status: {}", statementId, status.toString());
        statementService.updateStatementStatus(statementId, status);
        log.info("Statement By Id: {}, Status: {} successfully updated", statementId, status);
    }
}
