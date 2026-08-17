package com.example.asynclab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsyncLabApplication.class, args);
    }
}
