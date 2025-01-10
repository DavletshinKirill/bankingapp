package dev.davletshin.deal.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmailMessage {

    @Schema(description = "email пользователя", example = "kirill.davletshin.2022@gmail.com")
    @NotBlank(message = "email обязателен")
    @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$",
            message = "Email адрес не соответствует формату.")
    private String address;

    private UUID statementId;

    @Schema(description = "Текст письма",
            example = """
                    Мы рады сообщить вам, что ваш кредитный запрос был успешно одобрен!
                    Теперь вы можете воспользоваться средствами для реализации ваших планов. Подробности о сумме кредита, процентной ставке и условиях погашения вы найдете в приложении к этому письму.
                    Если у вас возникнут вопросы или потребуется дополнительная информация, не стесняйтесь обращаться к нам. Мы всегда готовы помочь!
                    Поздравляем вас и желаем удачи в ваших начинаниях!
                    """)
    @NotBlank(message = "Текст письма обязателен")
    private String text;

    @Schema(description = "Тема письма",
            example = "SEND_DOCUMENT",
            allowableValues = {"SEND_DOCUMENT", "REQUEST_SIGN_DOCUMENT", "SIGN_DOCUMENT"})
    @NotNull(message = "Тема письма обязателена")
    private Theme theme;
}
