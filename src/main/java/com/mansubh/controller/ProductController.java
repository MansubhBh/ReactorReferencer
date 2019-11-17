package com.mansubh.controller;

import com.mansubh.model.Product;
import com.mansubh.model.ProductEvent;
import com.mansubh.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository repository){
        this.productRepository = repository;
    }

    @GetMapping
    public Flux<Product> getAll(){
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id){

        return productRepository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product){
        return productRepository.save(product);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable("id") String id,
                                                       @RequestBody Product product){
       return productRepository.findById(id)
                .flatMap(product1 -> {
                    product1.setName(product.getName());
                    product1.setPrice(product.getPrice());
                    return productRepository.save(product1);
                })
                .map(updateProduct -> ResponseEntity.ok(updateProduct))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("id") String id) {
     return productRepository.findById(id)
                .flatMap(todeleteProduct ->
                        productRepository.delete(todeleteProduct)
                                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductEvent> getProductEvent(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(val -> new ProductEvent(val , "Product Event"));
    }

}
