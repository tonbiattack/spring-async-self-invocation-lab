package com.example.asynclab;

import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final AsyncWorker worker;

    public NotificationService(AsyncWorker worker) {
        this.worker = worker;
    }

    public CompletableFuture<String> submit(String message) {
        return worker.send(message);
    }

    public String workerThread() {
        return worker.workerThread();
    }
}
