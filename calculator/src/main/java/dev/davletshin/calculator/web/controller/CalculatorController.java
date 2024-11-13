package dev.davletshin.calculator.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
public class CalculatorController {

    @PostMapping("/offers")
    public void getOffers() {
    }

    @PostMapping("/calc")
    public void calculate() {
    }

}
