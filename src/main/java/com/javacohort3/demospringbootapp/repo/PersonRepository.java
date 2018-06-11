package com.javacohort3.demospringbootapp.repo;

import com.javacohort3.demospringbootapp.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long>{
    Person findPersonByFirstName(String firstName);

}
