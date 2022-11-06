package com.example.stock.facade;

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
class OptimisticLockStockFacadeTests {

    @Autowired
    private OptimisticLockStockFacade stockoptimisticLockStockFacade;
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
    void decreaseWithMultiThreadCase() throws InterruptedException {
        int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    this.stockoptimisticLockStockFacade.decrease(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
