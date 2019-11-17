package com.mansubh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxTest {

    @Test
    void firstFlux(){
        Flux.just("A","B","C")
                .log()
                .subscribe();
    }


    @Test
    void test() throws Exception{
        Flux.range(1, 10)
                .log()
                .subscribe(System.out::println);
    }


    @Test
    void fluxFromInterval() throws Exception{
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(2)
                .subscribe();
         Thread.sleep(5000);
    }

    @Test
    void fluxRequest(){
        Flux.range(1,5)
                .log()
                .subscribe(null,null,null, s -> s.request(3));
    }

    @Test
    void sometestonRequest(){
        Flux.range(1, 5)
                .log()
                .subscribe(null,null,null,s -> s.request(2));
    }
}
