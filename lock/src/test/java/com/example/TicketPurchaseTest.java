package com.example;


import com.example.service.TicketService;
import com.example.service.TicketServiceV1;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketPurchaseTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String TICKET_KEY = "ticket_stock";

    @BeforeAll
    void setup() throws Exception {
        redisTemplate.opsForValue().set(TICKET_KEY, "10");
    }

    @Test
    @DisplayName("동시성 테스트")
    public void concurrentPurchaseTest() throws Exception {

        //given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        ConcurrentLinkedQueue<Boolean> results = new ConcurrentLinkedQueue<>();

        //when
        IntStream.range(0, threadCount).forEach(i -> executorService.submit(() -> {
            try {
                startLatch.await();
                boolean result = ticketService.purchaseTicket();
                results.add(result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                endLatch.countDown();
            }
        }));

        startLatch.countDown();
        endLatch.await();

        long successCount = results.stream().filter(Boolean::booleanValue).count();
        long failureCount = results.stream().filter(success -> !success).count();

        //then
        assertThat(successCount).isEqualTo(10);
        assertThat(failureCount).isEqualTo(threadCount - 10);

        String finalStock = redisTemplate.opsForValue().get(TICKET_KEY);
        assertThat(finalStock).isEqualTo("0");

        System.out.println("성공한 구매 요청: " + successCount);
        System.out.println("실패한 구매 요청: " + failureCount);
        System.out.println("최종 재고 상태: " + finalStock);
    }
   
}
