package dev.davletshin.deal.service.impl;

import dev.davletshin.calculator.web.dto.EmailMessageDTO;
import dev.davletshin.calculator.web.dto.Theme;
import dev.davletshin.deal.service.interfaces.BrokerSender;
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
