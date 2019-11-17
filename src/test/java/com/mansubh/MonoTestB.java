package com.mansubh;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTestB {

    @Test
    void firstMono(){
        Mono.just("A")
                .log()
                .subscribe();
    }
}
