package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {
    private final StockRepository stockRepository;

    @Transactional
    public void decrease(final Long id, final Long quantity) {
        final Stock stock = this.stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        this.stockRepository.saveAndFlush(stock);
    }
}
