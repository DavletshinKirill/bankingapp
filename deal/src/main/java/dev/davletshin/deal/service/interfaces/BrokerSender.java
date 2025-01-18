package dev.davletshin.deal.service.interfaces;

import dev.davletshin.shared.dto.EmailMessageDTO;
import dev.davletshin.shared.dto.Theme;

public interface BrokerSender {
    void send(EmailMessageDTO emailMessage, Theme theme);
}
