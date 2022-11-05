package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTests {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;


    @BeforeEach
    public void before() {
        final Stock stock = new Stock(1L, 100L);
        this.stockRepository.save(stock);
    }

    @AfterEach
    public void after() {
        this.stockRepository.deleteAll();
    }

    @Test
    void decrease() {
        this.stockService.decrease(1L, 1L);
        final Stock stock = this.stockRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(99, stock.getQuantity());
    }
}
