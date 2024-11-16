package dev.davletshin.calculator.config;

import dev.davletshin.calculator.service.CalculateDifferentialLoanService;
import dev.davletshin.calculator.service.CalculateService;
import dev.davletshin.calculator.service.impl.CalculateDifferentialLoanServiceImpl;
import dev.davletshin.calculator.service.impl.CalculateServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


@TestConfiguration
public class TestConfig {

    @Primary
    @Bean
    public CalculateDifferentialLoanService calculateDifferentialLoanService() {
        return new CalculateDifferentialLoanServiceImpl();
    }

    @Primary
    @Bean
    public CalculateService calculateService() {
        return new CalculateServiceImpl(calculateDifferentialLoanService());
    }
}
