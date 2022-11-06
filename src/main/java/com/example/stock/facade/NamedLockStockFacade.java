package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {
    private final LockRepository lockRepository;
    private final StockService stockService;

    @Transactional
    public void decrease(final Long id, final Long quantity) {
        try {
            this.lockRepository.getLock(String.valueOf(id));
            this.stockService.decrease(id, quantity);
        } finally {
            this.lockRepository.release(String.valueOf(id));
        }
    }
}
