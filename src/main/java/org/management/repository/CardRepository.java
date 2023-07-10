package org.management.repository;

import org.management.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // find all Cards that owner of card has this nationalCode
    List<Card> findAllByPersonNationalCode(String nationalCode);

    // exists SenderId Of sender and PersonId Of Person in table
    boolean existsCardBySenderIdAndPersonId(Long senderId, Long personId);

    // exists nameCard in table
    boolean existsByNameCard(String nameCard);

}
