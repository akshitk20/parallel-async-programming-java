package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld(){

          return CompletableFuture.supplyAsync(helloWorldService::helloWorld) //supply async gives completable future and then accept gives the value of completable future.
                .thenApply(String::toUpperCase);

    }

    public CompletableFuture<String> helloWorld_withSize(){
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(s -> s.length()+" - "+s);
    }

    public String helloworld_multiple_async_calls(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);

            return hello
                        .thenCombine(world,(h,w) -> h+w) //combine first and second
                        .thenApply(String::toUpperCase)
                        .join();
    }

    public String helloworld_multiple_3_async_calls(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        return hello
                .thenCombine(world,(h,w) -> h+w) //combine first and second
                .thenCombine(hiCompletableFuture, (prev,current) -> prev+current)
                .thenApply(String::toUpperCase)
                .join();
    }

    public String helloworld_multiple_3_async_calls_log(){
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello,executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world,executorService);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        },executorService);
        return hello
                .thenCombine(world,(h,w) -> {
                    log("then Combine h/w");
                    return h+w;
                }) //combine first and second
                .thenCombine(hiCompletableFuture, (prev,current) -> {
                    log("then Combine prev/current");
                    return prev+current;
                })
                .thenApply(s -> {
                    log("thenApply ");
                    return s.toUpperCase();
                })
                .join();
    }

    public String helloworld_multiple_3_async_calls_log_async(){

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        return hello
                .thenCombineAsync(world,(h,w) -> {
                    log("then Combine h/w");
                    return h+w;
                }) //combine first and second
                .thenCombineAsync(hiCompletableFuture, (prev,current) -> {
                    log("then Combine prev/current");
                    return prev+current;
                })
                .thenApplyAsync(s -> {
                    log("thenApply ");
                    return s.toUpperCase();
                })
                .join();
    }

    public String helloworld_multiple_4_async_calls(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });
        return hello
                .thenCombine(world,(prev,curr) -> prev+curr)
                .thenCombine(hiCompletableFuture, (prev,curr) -> prev+curr)
                .thenCombine(bye,(prev,curr) -> prev+curr)
                .thenApply(String::toUpperCase)
                .join();
    }


    public CompletableFuture<String> helloWorld_thenCompose(){

        return CompletableFuture.supplyAsync(helloWorldService::hello) //supply async gives completable future and then accept gives the value of completable future.
                .thenCompose(prev -> helloWorldService.worldFuture(prev))
                .thenApply(String::toUpperCase);

    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld) //supply async gives completable future and then accept gives the value of completable future.
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> log("result "+result))  //result is the value inside completable future
                .join();  //join will block the caller thread until the result is completed or the whole computation is completed

        log("Done!");
//        delay(2000);
    }
}
