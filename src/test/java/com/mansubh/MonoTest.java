package com.mansubh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.sql.SQLOutput;

public class MonoTest {

    @Test
    void monoWithConsumer(){
        Mono.just("B")
                .log()
                .subscribe(System.out::println);
    }


    @Test
    void sometest(){
        Mono.error(new Exception())
                .onErrorResume(e -> {
                    System.out.println("Caught " + e);
                    return Mono.just("A");
                })
                .log()
                .subscribe();
    }

    @Test
    void monoWithDoOn() {
        Mono.just("A")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribed: " + subs))
                .doOnRequest(request -> System.out.println("Request: " + request))
                .doOnSuccess(complete -> System.out.println("Complete: " + complete))
                .subscribe(System.out::println);
    }

    @Test
    void emptyCompleteConsumerMono(){
        Mono.empty()
                .log()
                .subscribe(System.out::println, null, () -> System.out.println("done"));
    }


    @Test
    void someTestWithConsumer(){

    }
    @Test
    void errorDoOnErrorMono(){
        Mono.error(new Exception())
                .log()
                .subscribe(System.out::println, e -> System.out.println("Error " + e));
    }

    @Test
    void errorOnErrorResumeMono(){
        Mono.error(new Exception())
                .onErrorResume(e ->{
                        System.out.println("Caught : " + e);
                        return Mono.just("B");
                })
                .log()
                .subscribe();
    }
}
