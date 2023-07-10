package org.management.controller;

import org.management.dto.PersonDTO;
import org.management.service.PersonService;
import org.management.util.NumericStringChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String VALID_USERNAME = "milad";
    private static final String VALID_PASSWORD = "jskOIAPQWEres48651@454@!!__suqi";

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // save new person, remember the method has Basic Auth
    @PostMapping("/person")
    public ResponseEntity<PersonDTO> saveNewPerson(@RequestBody PersonDTO personDTO, @RequestHeader("Authorization") String authorizationHeader) {

        // Check if the Authorization header is present and starts with "Basic "
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // Extract and decode the credentials
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes);

            // Split the credentials into username and password
            String[] usernameAndPassword = credentials.split(":");

            // Validate the credentials
            String username = usernameAndPassword[0];
            String password = usernameAndPassword[1];
            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {

                // Continue with saving the new person
                validatePersonDTO(personDTO);
                logger.info("save new person : {}", personDTO);
                return new ResponseEntity<>(personService.save(personDTO), HttpStatus.OK);
            }

        }

        // Handle the case where the Authorization header is missing or not in the expected format
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    // getting all of Persons with using pagination
    @GetMapping("/person")
    public ResponseEntity<Page> getAllOfPersons(Pageable pageable) {
        logger.info("get All of persons with pagination : {}", pageable);
        return new ResponseEntity<>(personService.getAllOfPerson(pageable), HttpStatus.OK);
    }


    // validate PersonDto
    private void validatePersonDTO(PersonDTO personDTO) {
        if (personDTO.getId() != null) {
            logger.error("id person must be null");
            throw new IllegalArgumentException("id person must be null");
        }
        validateFieldNotNullOrEmpty(personDTO.getName(), "name");
        validateFieldNotNullOrEmpty(personDTO.getFamilyName(), "familyName");
        validateFieldNotNullOrEmpty(personDTO.getAddress(), "address");
        validateFieldNotNullOrEmpty(personDTO.getNationalCode(), "nationalCode");
        validateFieldNotNullOrEmpty(personDTO.getPhoneNumber(), "phoneNumber");
        validateNationalCodeAndPhoneNumber(personDTO.getNationalCode(), personDTO.getPhoneNumber());
    }

    // validate the String fields that shouldn't be null or empty
    private void validateFieldNotNullOrEmpty(String fieldValue, String fieldName) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            logger.error(fieldName + " of person should not be null or empty");
            throw new IllegalArgumentException(fieldName + " should not be null or empty");
        }
    }

    // validate the nationalCode and phone number
    private void validateNationalCodeAndPhoneNumber(String nationalCode, String phoneNumber) {

        // phone number should be made by numbers and the length is 11
        if (!NumericStringChecker.isNumeric(phoneNumber) || phoneNumber.length() != 11) {
            logger.error("Please enter the valid form of Phone Number");
            throw new IllegalArgumentException("Please enter the valid form of Phone Number");
        }

        // nationalCode should be made by numbers and the length is 10
        if (!NumericStringChecker.isNumeric(nationalCode) || nationalCode.length() != 10) {
            logger.error("Please enter the valid form of nationalCode");
            throw new IllegalArgumentException("Please enter the valid form of nationalCode");
        }

    }
}
