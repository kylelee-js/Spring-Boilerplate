package com.example.boilerplate;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTests {

    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Thread: " + Thread.currentThread().getName();
        });
        System.out.println("Thread1: " + Thread.currentThread().getName());
        System.out.println(future.get());
        System.out.println("Thread1: " + Thread.currentThread().getName());

    }
}
