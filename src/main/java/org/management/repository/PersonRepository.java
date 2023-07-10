package org.management.repository;

import org.management.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // exists nationalCode in table
    boolean existsByNationalCode(String nationalCode);

}
