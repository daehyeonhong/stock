package com.example.stock.facade;

import com.example.stock.RedisRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final RedisRepository redisRepository;
    private final StockService stockService;

    public void decrease(final Long id, final Long quantity) throws InterruptedException {
        while (Boolean.FALSE.equals(this.redisRepository.lock(id))) {
            Thread.sleep(100);
        }
        try {
            this.stockService.decrease(id, quantity);
        } finally {
            this.redisRepository.unlock(id);
        }
    }
}
