package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    //    @Transactional
    public synchronized void decrease(final Long id, final Long quantity) {
        //get Stock
        //decrease
        //save
        final Stock stock = this.stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
    }
}
