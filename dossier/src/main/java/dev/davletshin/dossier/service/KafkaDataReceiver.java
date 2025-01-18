package dev.davletshin.dossier.service;


import dev.davletshin.shared.dto.EmailMessageDTO;

public interface KafkaDataReceiver {
    void fetch(EmailMessageDTO emailMessageDTO);
}
