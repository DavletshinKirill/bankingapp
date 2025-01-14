package dev.davletshin.deal;

import dev.davletshin.deal.domain.Topic;
import dev.davletshin.deal.service.interfaces.DossierSender;
import dev.davletshin.deal.web.dto.EmailMessage;
import dev.davletshin.deal.web.dto.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;


@SpringBootApplication
@RequiredArgsConstructor
public class DealApplication implements ApplicationRunner {
    private final DossierSender dossierSender;
    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EmailMessage emailMessage = new EmailMessage("kirichka27@gmail.com", UUID.randomUUID(), "SOME TEXT", Theme.REQUEST_SIGN_DOCUMENT);
        dossierSender.send(emailMessage, Topic.SEND_DOCUMENTS);

    }
}
