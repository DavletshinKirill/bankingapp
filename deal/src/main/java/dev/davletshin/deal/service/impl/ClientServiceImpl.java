package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.client.Client;
import dev.davletshin.deal.repository.ClientRepository;
import dev.davletshin.deal.service.interfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
}
