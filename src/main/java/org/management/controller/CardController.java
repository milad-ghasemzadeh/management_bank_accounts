package org.management.controller;

import org.management.dto.CardDTO;
import org.management.dto.PersonDTO;
import org.management.dto.SenderDTO;
import org.management.service.CardService;
import org.management.util.NumericStringChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    private final CardService cardService;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Get all of Card with getting nationalCode of person
    @GetMapping("/card")
    public ResponseEntity<List<CardDTO>> getCards(@RequestParam String nationalCode) {

        if (!NumericStringChecker.isNumeric(nationalCode) || nationalCode.length() != 10) {
            logger.error("Please enter the valid form of nationalCode");
            throw new IllegalArgumentException("Please enter the valid form of nationalCode");
        }

        logger.info("getting cards for the person have this nationalCode : {} ", nationalCode);
        return new ResponseEntity<>(cardService.getCards(nationalCode), HttpStatus.OK);

    }

    // Create a new Card
    @PostMapping("/card")
    public ResponseEntity<CardDTO> saveNewCard(@RequestBody CardDTO cardDTO) {
        validateCardDTO(cardDTO);
        logger.info("save new card : {}", cardDTO);
        return new ResponseEntity<>(cardService.save(cardDTO), HttpStatus.OK);

    }

    // Validate CardDto ( information of CardDTO)
    private void validateCardDTO(CardDTO cardDTO) {
        if (cardDTO.getId() != null) {
            logger.error("id card must be null");
            throw new IllegalArgumentException("id card must be null");
        }
        validateFieldNotNullOrEmpty(cardDTO.getNameCard(), "nameCard");
        validateNameCard(cardDTO.getNameCard());

        if (cardDTO.getCardType() == null) {
            logger.error("cardType shouldn't be null");
            throw new IllegalArgumentException("cardType shouldn't be null");
        }
        validatePerson(cardDTO.getPerson());
        validateSender(cardDTO.getSender());
    }

    // check Not Null or of Empty String Fields
    private void validateFieldNotNullOrEmpty(String fieldValue, String fieldName) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            logger.error(fieldName + " of person should not be null or empty");
            throw new IllegalArgumentException(fieldName + " should not be null or empty");
        }
    }

    // Validate the Person
    private void validatePerson(PersonDTO personDTO) {
        if (personDTO == null || personDTO.getId() == null) {
            logger.error("Person and id of Person should not be null");
            throw new IllegalArgumentException("Person and id of Person should not be null");
        }

    }

    // validate Sender
    private void validateSender(SenderDTO senderDTO) {
        if (senderDTO == null || senderDTO.getId() == null) {
            logger.error("Sender and id of Sender should not be null");
            throw new IllegalArgumentException("Sender and id of Sender should not be null");
        }

    }

    // validate NameCard of Card
    private void validateNameCard(String nameCard) {
        if (!NumericStringChecker.isNumeric(nameCard) || nameCard.length() != 16) {
            logger.error("Please enter the valid form of nameCard");
            throw new IllegalArgumentException("Please enter the valid form of nameCard");
        }

    }

}
