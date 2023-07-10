package org.management.mapper;

import org.management.domain.Person;
import org.management.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    // convert Entity to Dto
    PersonDTO toDto(Person person);

    // convert Dto to Entity
    Person toEntity(PersonDTO personDTO);

}
