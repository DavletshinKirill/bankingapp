package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.config.TestConfig;
import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.repository.CreditRepository;
import dev.davletshin.deal.service.interfaces.CreditService;
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
class CreditServiceImplTest {
    @MockBean
    private CreditRepository creditRepository;

    @Autowired
    private CreditService creditService;

    @Test
    void createCredit() {
        Credit credit = Credit.builder()
                .id(UUID.randomUUID())
                .build();
        when(creditRepository.save(credit)).thenReturn(credit);
        Credit testCredit = creditService.createCredit(credit);
        verify(creditRepository).save(credit);
        assertEquals(credit, testCredit);
    }
}