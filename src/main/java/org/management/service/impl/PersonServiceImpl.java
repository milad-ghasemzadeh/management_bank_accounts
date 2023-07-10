package org.management.service.impl;

import org.management.domain.Person;
import org.management.dto.PersonDTO;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.mapper.PersonMapper;
import org.management.repository.PersonRepository;
import org.management.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    // save the new Person
    @Override
    public PersonDTO save(PersonDTO personDTO) {
        logger.info("save new Person");
        Person person = personMapper.toEntity(personDTO);

        // check new nationalCode for saving in the database.
        if (personRepository.existsByNationalCode(personDTO.getNationalCode())) {
            logger.error(ErrorMessageConstants.UniqueNationalCodePerson.developer_message);
            throw new CustomException(ErrorMessageConstants.UniqueNationalCodePerson.message,
                    ErrorMessageConstants.UniqueNationalCodePerson.developer_message,
                    ErrorMessageConstants.UniqueNationalCodePerson.status);
        }

        // save person
        person = personRepository.save(person);
        PersonDTO personResponse = personMapper.toDto(person);
        return personResponse;
    }

    // get all persons with pagination
    @Override
    public Page getAllOfPerson(Pageable pageable) {
        logger.info("get all of persons with pagination");
        return personRepository.findAll(pageable).map(personMapper::toDto);
    }
}
