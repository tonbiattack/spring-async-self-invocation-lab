package com.example.asynclab;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final AtomicReference<String> workerThread = new AtomicReference<>();

    public CompletableFuture<String> submit(String message) {
        return send(message);
    }

    @Async
    CompletableFuture<String> send(String message) {
        workerThread.set(Thread.currentThread().getName());
        return CompletableFuture.completedFuture("sent:" + message);
    }

    public String workerThread() {
        return workerThread.get();
    }
}
