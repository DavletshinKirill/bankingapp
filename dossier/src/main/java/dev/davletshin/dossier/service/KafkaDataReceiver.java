package dev.davletshin.dossier.service;


import dev.davletshin.calculator.web.dto.EmailMessageDTO;

public interface KafkaDataReceiver {
    void fetch(EmailMessageDTO emailMessageDTO);
}
