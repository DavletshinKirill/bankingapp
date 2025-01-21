package dev.davletshin.gateway.web.controller;


import dev.davletshin.gateway.service.CalculatorClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Calculator Controller", description = "Calculator API")
@Slf4j
public class CalculatorController {
    private final CalculatorClient calculatorClient;


}
