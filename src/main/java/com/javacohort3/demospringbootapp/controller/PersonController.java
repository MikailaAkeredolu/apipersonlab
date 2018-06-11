package com.javacohort3.demospringbootapp.controller;

import com.javacohort3.demospringbootapp.domain.Person;
import com.javacohort3.demospringbootapp.exception.ResourceNotFoundException;
import com.javacohort3.demospringbootapp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PersonController {

    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    protected void verifyID(Long id) throws ResourceNotFoundException {
        Person person = personService.getPerson(id);
        if (person == null) {
            throw new ResourceNotFoundException("Person with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getPersonList() {
        personService.getPersonList();
        return new ResponseEntity<>(personService.getPersonList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/people/{id}", method = RequestMethod.GET)
    public ResponseEntity<?>getPerson(@PathVariable Long id) {
        Person p = personService.getPerson(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHeroUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newHeroUri);
        if(p == null){
            verifyID(id);
            logger.info("p == null");
           return new ResponseEntity<Person>(null,responseHeaders, HttpStatus.NOT_FOUND);
        }else {
            logger.info("inside GET before return p");
           // return p;
            return new ResponseEntity<>(null, responseHeaders,HttpStatus.OK);
        }

    }



    @RequestMapping(value = "/people", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        personService.createPerson(person);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHeroUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId()).toUri();
        responseHeaders.setLocation(newHeroUri);
        logger.info("POST - person created in DB ");
        return new ResponseEntity<>(person, responseHeaders, HttpStatus.CREATED);

    }


    @RequestMapping(value = "/people/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Person person, @PathVariable Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHeroUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(person.getId()).toUri();
        responseHeaders.setLocation(newHeroUri);

          Person p = personService.getPerson(id);
            personService.updatePerson(person);

                if(p != null){
                    logger.info("updated person" + person);
                    return new ResponseEntity<>(person, responseHeaders, HttpStatus.OK);
                }else {
                    personService.createPerson(person);
                    logger.info("Created new person" + person);
                    return new ResponseEntity<>(person, responseHeaders, HttpStatus.CREATED);
                }

    }


    @RequestMapping(value = "/people/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable Long id) throws ResourceNotFoundException {
        verifyID(id);
        personService.deletePerson(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHeroUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newHeroUri);
        logger.info("Deleted user with ID - " + id + " Successfully");
        return new ResponseEntity<>(responseHeaders, HttpStatus.NO_CONTENT);
    }


}
