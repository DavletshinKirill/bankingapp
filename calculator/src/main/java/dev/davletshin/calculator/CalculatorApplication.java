package dev.davletshin.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo Доделай взаимоотношения deal и dossier
//todo проверь, что все работает
//todo dockerfiles
//todo entities in the controllers
//todo docker compose
//todo refactor github actions
//todo добавь более подробные логи
//todo допиши тесты
@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

}
