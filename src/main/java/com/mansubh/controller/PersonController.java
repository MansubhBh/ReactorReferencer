package com.mansubh.controller;


import com.mansubh.model.Person;
import com.mansubh.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    @GetMapping
    public Flux<Person> getAllPerson(){
        return personRepository.findAll();
    }

}
