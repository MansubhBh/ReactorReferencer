package com.mansubh.repository;

import com.mansubh.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person,Integer> {
}
