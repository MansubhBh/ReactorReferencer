package com.mansubh.handler;

import com.mansubh.model.Product;
import com.mansubh.model.ProductEvent;
import com.mansubh.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ProductHandler {

    private ProductRepository repository;

    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> products = repository.findAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> fetchedProduct = this.repository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();


        return fetchedProduct
                .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromObject(product)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<Product> productMono = request.bodyToMono(Product.class);

        return productMono
                .flatMap(product ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(this.repository.save(product), Product.class)
                );
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> existingProductMono = this.repository.findById(id);
        Mono<Product> productMono = request.bodyToMono(Product.class);

        Mono<ServerResponse> notFound = ServerResponse.ok().build();

        return productMono.zipWith(existingProductMono,
                (product, existingProduct) ->
                        new Product(existingProduct.getId(), product.getName(), product.getPrice()))
                .flatMap(product ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(this.repository.save(product), Product.class))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Product> productMono = this.repository.findById(id);

        Mono<ServerResponse> notFound = ServerResponse.ok().build();

        return productMono
                .flatMap(product ->
                        ServerResponse.ok()
                                .build(repository.delete(product)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getProductEvents(ServerRequest request){
        Flux<ProductEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ProductEvent(val , "Product events"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventFlux, ProductEvent.class);
    }


}
