package org.management.bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.management.controller.PersonController;
import org.management.dto.PersonDTO;
import org.management.service.PersonService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personController = new PersonController(personService);
    }

    @Test
    @DisplayName("test Save New Person With Valid Credentials And ValidPersonDTO")
    void testSaveNewPerson_WithValidCredentialsAndValidPersonDTO_ReturnsSavedPerson() {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Mahmoad");
        personDTO.setFamilyName("Nabi");
        personDTO.setAddress("Farmanieh, Tehran, Iran");
        personDTO.setNationalCode("0056545454");
        personDTO.setPhoneNumber("09125555556");

        String validCredentials = "milad:jskOIAPQWEres48651@454@!!__suqi";
        String authorizationHeader = "Basic " + Base64.getEncoder().encodeToString(validCredentials.getBytes());

        when(personService.save(personDTO)).thenReturn(personDTO);

        // Act
        ResponseEntity<PersonDTO> response = personController.saveNewPerson(personDTO, authorizationHeader);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
        verify(personService).save(personDTO);
    }

    @ParameterizedTest
    @MethodSource("invalidPersonDate")
    @DisplayName("test Save New Person With Valid Credentials And InValid PersonDTO")
    void testSaveNewPerson_WithValidCredentialsAndInValidPersonDTO_ThrowsException(PersonDTO personDTO) {

        // Arrange
        String validCredentials = "milad:jskOIAPQWEres48651@454@!!__suqi";
        String authorizationHeader = "Basic " + Base64.getEncoder().encodeToString(validCredentials.getBytes());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> personController.saveNewPerson(personDTO, authorizationHeader));

    }

    private static Stream<PersonDTO> invalidPersonDate() {


        //id person must be null
        PersonDTO invalidPersonDTO1 = new PersonDTO();
        invalidPersonDTO1.setId(1l);

        //name of person should not be null or empty
        PersonDTO invalidPersonDTO2 = new PersonDTO();

        //familyName of person should not be null or empty
        PersonDTO invalidPersonDTO3 = new PersonDTO();
        invalidPersonDTO3.setName("Ahmad");

        //address of person should not be null or empty
        PersonDTO invalidPersonDTO4 = new PersonDTO();
        invalidPersonDTO4.setName("Ahmad");
        invalidPersonDTO4.setFamilyName("Ahmadian");

        // nationalCode of person should not be null or empty
        PersonDTO invalidPersonDTO5 = new PersonDTO();
        invalidPersonDTO5.setName("Ahmad");
        invalidPersonDTO5.setFamilyName("Ahmadian");
        invalidPersonDTO5.setAddress("Yazd, Iran");

        //phoneNumber of person should not be null or empty
        PersonDTO invalidPersonDTO6 = new PersonDTO();
        invalidPersonDTO6.setName("Ahmad");
        invalidPersonDTO6.setFamilyName("Ahmadian");
        invalidPersonDTO6.setAddress("Yazd, Iran");
        invalidPersonDTO6.setNationalCode("4545458454");

        //Please enter the valid form of Phone Number
        PersonDTO invalidPersonDTO7 = new PersonDTO();
        invalidPersonDTO7.setName("Ahmad");
        invalidPersonDTO7.setFamilyName("Ahmadian");
        invalidPersonDTO7.setAddress("Yazd, Iran");
        invalidPersonDTO7.setNationalCode("4545458454");
        invalidPersonDTO7.setPhoneNumber("0913454544");

        //Please enter the valid form of nationalCode
        PersonDTO invalidPersonDTO8 = new PersonDTO();
        invalidPersonDTO8.setName("Ahmad");
        invalidPersonDTO8.setFamilyName("Ahmadian");
        invalidPersonDTO8.setAddress("Yazd, Iran");
        invalidPersonDTO8.setNationalCode("11254");
        invalidPersonDTO8.setPhoneNumber("0913454544");

        return Stream.of(
                invalidPersonDTO1,
                invalidPersonDTO2,
                invalidPersonDTO3,
                invalidPersonDTO4,
                invalidPersonDTO5,
                invalidPersonDTO6,
                invalidPersonDTO6,
                invalidPersonDTO7,
                invalidPersonDTO8
        );
    }

    @Test
    @DisplayName("test Save New Person With Invalid Credentials")
    void testSaveNewPerson_WithInvalidCredentials_ReturnsUnauthorized() {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        String invalidCredentials = "invalid:password";
        String authorizationHeader = "Basic " + Base64.getEncoder().encodeToString(invalidCredentials.getBytes());

        // Act
        ResponseEntity<PersonDTO> response = personController.saveNewPerson(personDTO, authorizationHeader);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(personService, never()).save(any());
    }

    @Test
    @DisplayName("test Get All Of Persons")
    void testGetAllOfPersons_ReturnsListOfPersons() {
        // Arrange
        Page<PersonDTO> persons = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(personService.getAllOfPerson(pageable)).thenReturn(persons);

        // Act
        ResponseEntity<Page> response = personController.getAllOfPersons(pageable);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(persons, response.getBody());
        verify(personService).getAllOfPerson(pageable);
    }

}
