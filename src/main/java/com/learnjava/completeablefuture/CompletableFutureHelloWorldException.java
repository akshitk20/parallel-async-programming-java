package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {


    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    public String helloworld_multiple_3_async_calls_handle(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        return hello
                .handle((res,e) ->{
                    log("res is "+ res);
                    if(e!= null){
                        log("Exception is " + e.getMessage());
                        return ""; // provide recoverable value
                    }else return res;

                })
                .thenCombine(world,(h,w) -> h+w) //combine first and second
                .handle((res,e) ->{
                    log("res is "+ res);
                    if(e!=null){
                        log("Exception after world is " + e.getMessage());
                        return "";
                    }else return res;

                })
                .thenCombine(hiCompletableFuture, (prev,current) -> prev+current)
                .thenApply(String::toUpperCase)
                .join();

    }

    public String helloworld_multiple_3_async_calls_exceptionally(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        return hello
                .exceptionally((e) ->{ //no need to exceptionally checking if this is null like in handle. It will only get invoked if there is exception.
                    log("Exception is " + e.getMessage());
                    return ""; // provide recoverable value
                })
                .thenCombine(world,(h,w) -> h+w) //combine first and second
                .exceptionally((e) ->{
                    log("Exception after world is " + e.getMessage());
                    return "";
                })
                .thenCombine(hiCompletableFuture, (prev,current) -> prev+current)
                .thenApply(String::toUpperCase)
                .join();

    }

    public String helloworld_multiple_3_async_calls_whenhandle(){
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi CompletableFuture";
        });
        return hello
                .whenComplete((res,e) ->{ //Biconsumer takes two input but does not return anything. BiFunction takes two input and returns output
                    log("res is "+ res);
                    if(e!= null){
                        log("Exception is " + e.getMessage());
                    }
                })
                .thenCombine(world,(h,w) -> h+w) //combine first and second
                .whenComplete((res,e) ->{
                    log("res is "+ res);
                    if(e!=null){
                        log("Exception after world is " + e.getMessage());
                    }

                })
                .thenCombine(hiCompletableFuture, (prev,current) -> prev+current)
                .thenApply(String::toUpperCase)
                .join();

    }


}
