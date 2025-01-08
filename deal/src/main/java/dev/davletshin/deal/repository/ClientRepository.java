package dev.davletshin.deal.repository;

import dev.davletshin.deal.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
