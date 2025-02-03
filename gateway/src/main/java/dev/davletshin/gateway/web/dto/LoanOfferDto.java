package dev.davletshin.gateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanOfferDto {
    private UUID statementId;

    @Schema(description = "Запрашиваемая сумма кредита", example = "40000")
    @NotNull(message = "Запрашиваемая сумма кредита обязательна")
    @DecimalMin(value = "20000", message = "Запрашиваемая сумма кредита должны быть больше или равна 20 000", inclusive = false)
    private BigDecimal requestedAmount;

    @Schema(description = "Общая сумма кредита", example = "41283")
    @NotNull(message = "Общая сумма кредита обязательна")
    @DecimalMin(value = "20000", message = "Общая сумма кредита должны быть больше или равна 20 000", inclusive = false)
    private BigDecimal totalAmount;

    @Schema(description = "Срок на который берется кредит", example = "6")
    @NotNull(message = "Срок кредита обязателен")
    @Min(value = 6, message = "Срок кредита должен быть больше или равен 6")
    private int term;

    @Schema(description = "Средняя ежемесячная выплата", example = "6881")
    @NotNull(message = "Средняя ежемесячная выплата")
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка", example = "1")
    @NotNull(message = "Процентная ставка")
    private BigDecimal rate;

    @Schema(description = "Страховка клиента", example = "true")
    @NotNull(message = "Страховка клиента не должна быть пустой")
    private boolean isInsuranceEnabled;

    @Schema(description = "Трудоустройство клиента", example = "true")
    @NotNull(message = "Трудоустройство клиента не должна быть пустой")
    private boolean isSalaryClient;
}
