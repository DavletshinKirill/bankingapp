package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.Topic;
import dev.davletshin.deal.service.interfaces.DossierSender;
import dev.davletshin.deal.web.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class DossierSenderImpl implements DossierSender {

    private final KafkaSender<String, Object> sender;

    @Override
    public void send(EmailMessage emailMessage, Topic topic) {
        sender.send(
                        Mono.just(
                                SenderRecord.create(
                                        topic.getTopicTitle(),
                                        0,
                                        System.currentTimeMillis(),
                                        String.valueOf(emailMessage.hashCode()),
                                        emailMessage,
                                        null
                                )
                        )
                )
                .subscribe();
    }
}
