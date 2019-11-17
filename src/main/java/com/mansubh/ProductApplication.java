package com.mansubh;

import com.mansubh.handler.ProductHandler;
import com.mansubh.model.Person;
import com.mansubh.model.Product;
import com.mansubh.repository.PersonRepository;
import com.mansubh.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PersonRepository personRepository){
        return args -> {
            Flux<Person> personFlux = Flux.just(new Person("213","John Doe",21,"1 main st"),
                    new Person("432","Filip Doe",34,"2 shel st"),
                    new Person("854","Mike Ross",28,"54 station st"))
                    .flatMap(personRepository::save);

            personFlux.thenMany(personRepository.findAll())
                    .subscribe(System.out::println);
        };
    }


   /* @Bean
    RouterFunction<ServerResponse> route(ProductHandler handler) {

        return nest(path("/v2/products"),
                nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
                        route(GET("/"), handler::getAllProducts)
                                .andRoute(method(HttpMethod.POST), handler::saveProduct)
                                .andRoute(GET("/events"), handler::getProductEvents)
                                .andNest(path("/{id}"),
                                        route(method(HttpMethod.PUT), handler::updateProduct)
                                                .andRoute(method(HttpMethod.DELETE), handler::deleteProduct))
                )
        );

        return route(GET("/v2/products").and(accept(APPLICATION_JSON)),handler::getAllProducts)
                .andRoute(POST("/v2/products").and(contentType(APPLICATION_JSON)),handler::saveProduct);


    }*/
}
