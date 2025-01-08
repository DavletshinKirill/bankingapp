package dev.davletshin.deal.repository;

import dev.davletshin.deal.domain.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
}
