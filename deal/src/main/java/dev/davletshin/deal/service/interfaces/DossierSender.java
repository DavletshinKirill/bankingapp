package dev.davletshin.deal.service.interfaces;

import dev.davletshin.deal.domain.Topic;
import dev.davletshin.deal.web.dto.EmailMessage;

public interface DossierSender {
    void send(EmailMessage emailMessage, Topic topic);
}
