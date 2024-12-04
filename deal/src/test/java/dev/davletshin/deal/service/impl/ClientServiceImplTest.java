package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.repository.ClientRepository;
import dev.davletshin.deal.service.interfaces.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    void createClient() {
        Client client = Client.builder()
                .id(UUID.randomUUID())
                .build();
        when((clientRepository.save(client))).thenReturn(client);

        Client testClient = clientService.createClient(client);
        verify(clientRepository).save(client);
        assertEquals(client, testClient);
    }
}