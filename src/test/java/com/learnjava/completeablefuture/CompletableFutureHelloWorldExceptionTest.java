package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {
    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException hwcf;

    @Test
    void test(){

        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcf.helloworld_multiple_3_async_calls_handle();

        assertEquals(" WORLD! HI COMPLETABLEFUTURE",result);
    }

    @Test
    void test1(){
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred"));

        String result = hwcf.helloworld_multiple_3_async_calls_handle();

        assertEquals(" HI COMPLETABLEFUTURE",result);

    }


    @Test
    void test2(){
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcf.helloworld_multiple_3_async_calls_handle();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);

    }

    @Test
    void test3(){
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = hwcf.helloworld_multiple_3_async_calls_exceptionally();

        assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE",result);

    }

    @Test
    void test4(){
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred"));

        String result = hwcf.helloworld_multiple_3_async_calls_exceptionally();

        assertEquals(" HI COMPLETABLEFUTURE",result);

    }


}