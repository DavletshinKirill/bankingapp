package dev.davletshin.deal.service.interfaces;


import dev.davletshin.deal.domain.enums.Theme;
import dev.davletshin.deal.web.dto.EmailMessageDTO;

public interface BrokerSender {
    void send(EmailMessageDTO emailMessage, Theme theme);
}
