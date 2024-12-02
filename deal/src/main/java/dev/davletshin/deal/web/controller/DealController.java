package dev.davletshin.deal.web.controller;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.service.interfaces.DealService;
import dev.davletshin.deal.web.dto.LoanOfferDto;
import dev.davletshin.deal.web.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Tag(name = "Deal Controller", description = "Deal API")
public class DealController {

    private final DealService dealService;

    @Operation(summary = "createClientAndStatement", description = "Create 4 offers")
    @PostMapping("/statement")
    public List<LoanOfferDto> createClientAndStatement(LoanStatementRequestDto loanStatementRequestDto) {
        Client client = Client.builder()
                .email(loanStatementRequestDto.getEmail())
                .birthdate(loanStatementRequestDto.getBirthdate())
                .firstName(loanStatementRequestDto.getFirstName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .lastName(loanStatementRequestDto.getLastName())
                .build();
        return dealService.createClientAndStatement(loanStatementRequestDto, client);
    }
}
