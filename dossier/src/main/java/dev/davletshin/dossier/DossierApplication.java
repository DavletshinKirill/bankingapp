package dev.davletshin.dossier;

import dev.davletshin.dossier.service.MailService;
import dev.davletshin.shared.dto.EmailMessageDTO;
import dev.davletshin.shared.dto.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;


@SpringBootApplication
@RequiredArgsConstructor
public class DossierApplication implements ApplicationRunner {
    private final MailService mailService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EmailMessageDTO emailMessageDTO = new EmailMessageDTO("kirichka27@gmail.com", UUID.randomUUID(), "Ерков Дмитрий Алексеевич", Theme.FINISH_REGISTRATION);
        mailService.sendEmail(emailMessageDTO);
    }

    public static void main(String[] args) {
        SpringApplication.run(DossierApplication.class, args);
    }

}
