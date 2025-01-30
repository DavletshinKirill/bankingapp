package dev.davletshin.dossier.service.impl;


import dev.davletshin.dossier.dto.ApplicationStatus;
import dev.davletshin.dossier.dto.EmailMessageDTO;
import dev.davletshin.dossier.dto.Theme;
import dev.davletshin.dossier.service.DealClient;
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

    private final MailService mailService;
    private final DealClient dealClient;

    @Override
    @SneakyThrows
    @KafkaListener(topics = {"finish-registration", "send-documents", "send-ses", "credit-issued"},
            groupId = "${kafka.group-id}")
    public void fetch(@Payload EmailMessageDTO emailMessageDTO) {
        log.info("Received email message: {}", emailMessageDTO.toString());
        mailService.sendEmail(emailMessageDTO);
        ApplicationStatus applicationStatus = null;
        switch (emailMessageDTO.getTheme()) {
            case CREATE_DOCUMENTS -> applicationStatus = ApplicationStatus.DOCUMENT_CREATED;
            case CREDIT_ISSUED -> applicationStatus = ApplicationStatus.CREDIT_ISSUED;
        }
        if (applicationStatus != null) {
            dealClient.updateStatementStatus(emailMessageDTO.getStatementId(), applicationStatus);
            log.info("Updating statement with id {} statement status to {}", emailMessageDTO.getStatementId(), applicationStatus);
        }
    }
}
