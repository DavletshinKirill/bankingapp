package dev.davletshin.deal.service.interfaces;


import dev.davletshin.calculator.web.dto.EmailMessageDTO;
import dev.davletshin.calculator.web.dto.Theme;

public interface BrokerSender {
    void send(EmailMessageDTO emailMessage, Theme theme);
}
