package com.example.stock.facade;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {
    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(final Long id, final Long quantity) {
        final RLock lock = this.redissonClient.getLock(String.valueOf(id));
        try {
            final boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                log.info("fail getLock");
                return;
            }
            this.stockService.decrease(id, quantity);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
