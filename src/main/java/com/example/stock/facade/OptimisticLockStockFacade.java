package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {
    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(final Long id, final Long quantity) throws InterruptedException {
        while (true) {
            try {
                this.optimisticLockStockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
