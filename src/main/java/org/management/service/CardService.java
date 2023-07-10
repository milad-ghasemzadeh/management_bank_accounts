package org.management.service;

import org.management.dto.CardDTO;

import java.util.List;

public interface CardService {

    // get all cards for specific person that who has this nationalCode.
    List<CardDTO> getCards(String nationalCode);

    //save the new Card
    CardDTO save(CardDTO cardDTO);

}
