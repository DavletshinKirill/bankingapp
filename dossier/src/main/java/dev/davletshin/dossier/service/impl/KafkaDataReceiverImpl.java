package dev.davletshin.dossier.service.impl;

import dev.davletshin.dossier.service.KafkaDataReceiver;
import dev.davletshin.dossier.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final MailService mailService;

    @Override
    public void fetch() {
        receiver.receive()
                .subscribe(r -> System.out.println("Received: " + r.value())

                );
    }
}
