package dev.davletshin.deal.service.impl;

import dev.davletshin.deal.domain.credit.Credit;
import dev.davletshin.deal.repository.CreditRepository;
import dev.davletshin.deal.service.interfaces.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Override
    public Credit createCredit(Credit credit) {
        return creditRepository.save(credit);
    }
}
