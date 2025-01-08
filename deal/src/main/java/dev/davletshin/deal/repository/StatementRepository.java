package dev.davletshin.deal.repository;

import dev.davletshin.deal.domain.statement.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {
}
