package dev.davletshin.dossier.service.impl;


import dev.davletshin.dossier.service.KafkaDataReceiver;
import dev.davletshin.dossier.service.MailService;
import dev.davletshin.shared.dto.EmailMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private static final String groupId = "${kafka.group-id}";
    private final MailService mailService;

    @Override
    @SneakyThrows
    @KafkaListener(topics = {"finish-registration", "send-documents", "send-ses", "credit-issued"},
            groupId = groupId, containerFactory = "kafkaListenerContainerFactory")
    public void fetch(EmailMessageDTO emailMessageDTO) {
        log.info("Received email message: {}", emailMessageDTO.toString());
        mailService.sendEmail(emailMessageDTO);
    }
}
