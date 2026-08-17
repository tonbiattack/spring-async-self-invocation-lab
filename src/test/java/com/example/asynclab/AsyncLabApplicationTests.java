package com.example.asynclab;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AsyncLabApplicationTests {
    @Autowired
    NotificationService service;

    @Test
    void submit_runs_on_a_worker_thread() throws Exception {
        String callerThread = Thread.currentThread().getName();

        CompletableFuture<String> result = service.submit("invoice-1");

        assertThat(result.get()).isEqualTo("sent:invoice-1");
        assertThat(service.workerThread()).isNotEqualTo(callerThread);
    }
}
