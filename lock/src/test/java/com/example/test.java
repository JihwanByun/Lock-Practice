package com.example;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.example.entity.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@SpringBootTest
public class test {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void test() {
        Session session = entityManager.unwrap(Session.class);
        Ticket ticket = new Ticket();
        session.persist(ticket);
        session.find(Ticket.class, 1L);
        ticket.setSeatType("CV");
        session.flush();
    }

    @Test
    void async() throws InterruptedException {
//        CompletableFuture.supplyAsync(() -> {
//            return "aa";
//        }).thenApply(string -> {
//            return "bb";
//        }).thenAccept(System.out::println);

        ConnectableFlux<Long> hot = Flux.interval(Duration.ofMillis(300))
                                        .publish(); // ConnectableFlux

        hot.connect();

        Thread.sleep(500);
        hot.subscribe(i -> System.out.println("Late subscriber: " + i));

    }


}
