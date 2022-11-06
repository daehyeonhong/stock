package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StockServiceTests {
    @Autowired
    private PessimisticLockStockService stockService;
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
        assertEquals(99, stock.getQuantity());
    }

    @Test
    void decreaseWithMultiThreadCase() throws InterruptedException {
        int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    this.stockService.decrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        final Stock stock = stockRepository.findById(1L).orElseThrow();
        assertEquals(0, stock.getQuantity());
    }
}
