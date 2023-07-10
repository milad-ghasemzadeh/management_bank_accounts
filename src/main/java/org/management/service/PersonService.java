package org.management.service;

import org.management.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    // save new Person
    PersonDTO save(PersonDTO personDTO);

    // get a page of Person
    Page getAllOfPerson(Pageable pageable);

}
