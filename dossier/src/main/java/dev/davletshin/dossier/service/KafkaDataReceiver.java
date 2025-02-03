package dev.davletshin.dossier.service;


import dev.davletshin.dossier.dto.EmailMessageDTO;

public interface KafkaDataReceiver {
    void fetch(EmailMessageDTO emailMessageDTO);
}
