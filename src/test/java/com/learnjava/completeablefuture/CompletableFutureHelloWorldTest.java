package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void Test(){

        CompletableFuture<String> response = completableFutureHelloWorld.helloWorld();
        response.thenAccept(s -> {
            assertEquals("HELLO WORLD",s);
        }).join();

    }

    @Test
    void helloWorld_withSize_test(){
        CompletableFuture<String> response = completableFutureHelloWorld.helloWorld_withSize();
        response.thenAccept(s->{
            assertEquals("11 - HELLO WORLD",s);
        }).join();

    }

    @Test
    void helloworld_multiple_3_async_calls(){
        String result = completableFutureHelloWorld.helloworld_multiple_3_async_calls();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);
    }

    @Test
    void helloworld_multiple_3_async_calls_threadpool(){
        String result = completableFutureHelloWorld.helloworld_multiple_3_async_calls_log();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);
    }
    @Test
    void helloworld_multiple_4_async_calls(){
        String result = completableFutureHelloWorld.helloworld_multiple_4_async_calls();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE BYE!",result);
    }

    @Test
    void Test1(){
        startTimer();
        CompletableFuture<String> response = completableFutureHelloWorld.helloWorld_thenCompose();
        response.thenAccept(s -> {
            assertEquals("HELLO WORLD!",s);
        }).join();
        timeTaken();
    }

    @Test
    void helloworld_multiple_3_async_calls_log(){
        String result = completableFutureHelloWorld.helloworld_multiple_3_async_calls_log();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);
    }

    @Test
    void helloworld_multiple_3_async_calls_log_async(){
        String result = completableFutureHelloWorld.helloworld_multiple_3_async_calls_log_async();
        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);
    }
}