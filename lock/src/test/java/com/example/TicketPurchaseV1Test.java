package com.example;

import com.example.entity.Ticket;
import com.example.repository.TicketRepository;
import com.example.service.TicketServiceV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TicketPurchaseV1Test {

    @Autowired
    TicketServiceV1 ticketServiceV1;
    @Autowired
    TicketRepository ticketRepository;

    @AfterEach
    public void after() {
        ticketRepository.deleteAll();
    }

    @Test
    @DisplayName("TicketServiceV1 동시성 테스트")
    public void purchaseTicket() throws Exception {
        // given
        Ticket ticket = new Ticket(1L, 10L); //  초기 재고=10
        ticketRepository.save(ticket);
        int threadCount = 50; // 동시 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount); // 모든 스레드가 작업을 끝날 때까지 대기

        // when
        IntStream.range(0, threadCount).forEach(i -> executorService.submit(() -> {
            try {
                ticketServiceV1.purchaseTicket(1L, 1L); // 티켓 구매 시도
            } finally {
                latch.countDown(); // 작업 완료 카운트
            }
        }));

        latch.await();
        // then
        System.out.println("남은 재고: " + ticket.getTotalStock());
        assertThat(ticket.getTotalStock()).isEqualTo(0L);
    }
}
