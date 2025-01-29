package dev.davletshin.dossier.service.impl;


import dev.davletshin.dossier.dto.EmailMessageDTO;
import dev.davletshin.dossier.service.KafkaDataReceiver;
import dev.davletshin.dossier.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private static final String kafkaGroupId = "${kafka.group-id}";
    private final MailService mailService;

    @Override
    @SneakyThrows
    @KafkaListener(topics = {"finish-registration", "send-documents", "send-ses", "credit-issued"},
            groupId = kafkaGroupId)
    public void fetch(@Payload EmailMessageDTO emailMessageDTO) {
        log.info("Received email message: {}", emailMessageDTO.toString());
        mailService.sendEmail(emailMessageDTO);
    }
}
