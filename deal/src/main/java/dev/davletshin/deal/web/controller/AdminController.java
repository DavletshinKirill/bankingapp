package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.domain.statement.Statement;
import dev.davletshin.deal.service.interfaces.StatementService;
import dev.davletshin.deal.web.dto.StatementDto;
import dev.davletshin.deal.web.mapper.StatementMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/deal/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Admin API")
@Slf4j
public class AdminController {

    private final StatementService statementService;
    private final StatementMapper statementMapper;

    @Operation(summary = "getStatementById", description = "Get Statement By Id")
    @GetMapping("/statement/{statementId}")
    public StatementDto getStatementById(@PathVariable UUID statementId) {
        Statement statement = statementService.getStatement(statementId);
        StatementDto statementDto = statementMapper.toDTO(statement);
        log.info("Get Statement By Id: {}", statementDto.toString());
        return statementDto;
    }

    @Operation(summary = "getAllStatements", description = "Get All Statements")
    @GetMapping("/statement")
    public List<StatementDto> getAllStatements() {
        List<Statement> statements = statementService.getStatements();
        List<StatementDto> statementDtoList = statementMapper.toDTO(statements);
        log.info("Get All Statements: {}", statementDtoList.toString());
        return statementDtoList;
    }
}
