package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.service.interfaces.BrokerSender;
import dev.davletshin.shared.dto.EmailMessageDTO;
import dev.davletshin.shared.dto.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrokerSenderImpl implements BrokerSender {

    private final KafkaTemplate<String, Object> template;

    @Override
    public void send(EmailMessageDTO emailMessage, Theme theme) {
        template.send(theme.getTopicTitle(), emailMessage);
    }
}
