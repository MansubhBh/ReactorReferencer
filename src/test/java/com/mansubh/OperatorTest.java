package com.mansubh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest {

    @Test
    void map(){
        Flux.range(1,5)
                .map( i -> i * 10)
                .subscribe(System.out::println);
    }




    @Test
    void flatMap(){
        Flux.range(1, 5)
                .flatMap(i -> Flux.range(i*10, 2))
                .subscribe(System.out::println);
    }


    @Test
    void testFlatMap(){
        Mono.just(3)
                .flatMapMany(i -> Flux.range(1, i))
                .subscribe(System.out::println);

    }

    @Test
    void simpleTest(){
        Flux.range(1, 5)
                .log()
                .flatMap(i -> Flux.range(i , 2))
                .subscribe(System.out::println);
    }

    @Test
    void concat() throws Exception {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

//        Flux.concat(oneToFive, sixToTen)
//                .subscribe(System.out::println);

        oneToFive.concatWith(sixToTen)
                .subscribe(System.out::println);

        //Thread.sleep(5000);
    }

    @Test
    void merge() throws Exception {
        Flux<Integer> oneToFive = Flux.range(1, 5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6, 5)
                .delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFive,sixToTen)
                .subscribe(System.out::println);

        Thread.sleep(5000);
    }

    @Test
    void zip() throws Exception {
        Flux<Integer> oneToFive = Flux.range(1, 5);
        Flux<Integer> sixToTen = Flux.range(6, 5);
        Flux.zip(oneToFive, sixToTen,
                (item1,item2) -> item1 + ", " + item2)
                .subscribe(System.out::println);
        
    }

}
