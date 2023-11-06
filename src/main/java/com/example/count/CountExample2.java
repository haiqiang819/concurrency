package com.example.count;

import com.example.annotations.NotThreadSafe;
import com.example.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class CountExample2 {
    public static int clientTotal = 5000;
    public static int threadTotal = 200;

    // int -> AtomicInteger
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        service.shutdown();
        // log.info("count:{}", count); count -> count.get()
        log.info("count:{}", count.get());
    }

    // ThreadSafe
    // count++ -> count.incrementAndGet()
    private static void add() {
        count.incrementAndGet(); // count.getAndIncrement()
    }
}