package com.javacohort3.demospringbootapp.service;

import com.javacohort3.demospringbootapp.domain.Person;
import com.javacohort3.demospringbootapp.repo.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PersonService {
    public static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    PersonRepository personRepository;

    public List<Person> getPersonList() {
        List<Person> people = new ArrayList<>();
        personRepository.findAll().forEach(people::add);
        return people;
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);

    }


    public Person updatePerson(Person person) {
       return personRepository.save(person);
    }



    public Person getPerson(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
            if (personOptional.isPresent()) {
                return personOptional.get();
            }else{
                return null;
            }
        }


    public Person findPersonByFirstName(String firstName) {
        return personRepository.findPersonByFirstName(firstName);
    }


    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }


}


















//       Optional<Person> foundPerson = personRepository.findById(person.getId());
//        if(!foundPerson.isPresent()){
//            logger.info("inside updatePerson service");
//            throw new ResourceNotFoundException("update service id is NULL");
//        }
//        personRepository.save(person);
//        return foundPerson;

// throw new ResourceNotFoundException("update service id is NULL");