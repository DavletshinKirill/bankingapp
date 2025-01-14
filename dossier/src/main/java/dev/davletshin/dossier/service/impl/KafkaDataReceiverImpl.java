package dev.davletshin.dossier.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.davletshin.dossier.dto.EmailMessageDTO;
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
        Gson gson = new GsonBuilder().create();
        receiver.receive()
                .subscribe(r -> {
                            System.out.println("Received: " + r.value());
                            EmailMessageDTO emailMessageDTO = gson
                                    .fromJson(r.value().toString(), EmailMessageDTO.class);
                        }
                );
    }
}
