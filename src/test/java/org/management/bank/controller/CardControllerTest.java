package org.management.bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.management.controller.CardController;
import org.management.dto.CardDTO;
import org.management.dto.PersonDTO;
import org.management.dto.SenderDTO;
import org.management.enums.CardType;
import org.management.service.CardService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardControllerTest {

    @Mock
    private CardService cardService;

    private CardController cardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardController = new CardController(cardService);
    }

    @Test
    @DisplayName("test Get Cards With Valid NationalCode")
    void testGetCards_WithValidNationalCode_ReturnsListOfCards() {
        // Arrange
        String nationalCode = "1234567890";
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO());

        when(cardService.getCards(nationalCode)).thenReturn(cards);

        // Act
        ResponseEntity<List<CardDTO>> response = cardController.getCards(nationalCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cards, response.getBody());
        verify(cardService).getCards(nationalCode);
    }

    @ParameterizedTest
    @MethodSource("invalidNationalCodes")
    @DisplayName("test Get Cards With Invalid NationalCode")
    void testGetCards_WithInvalidNationalCode_ThrowsException(String nationalCode) {

        assertThrows(IllegalArgumentException.class, () -> cardController.getCards(nationalCode));

    }

    private static Stream<String> invalidNationalCodes() {
        return Stream.of(
                "54",
                "sdjsdhsdjhsd",
                "454545@sdjsdhjsd",
                "111111111"
        );
    }


    @Test
    @DisplayName("test Save New Card With Valid CardDTO")
    void testSaveNewCard_WithValidCardDTO_ReturnsSavedCard() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNameCard("6037995454984545");
        cardDTO.setCardType(CardType.cash);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1l);
        SenderDTO senderDTO = new SenderDTO();
        senderDTO.setId(1l);

        cardDTO.setPerson(personDTO);
        cardDTO.setSender(senderDTO);

        when(cardService.save(cardDTO)).thenReturn(cardDTO);

        // Act
        ResponseEntity<CardDTO> response = cardController.saveNewCard(cardDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardDTO, response.getBody());
        verify(cardService).save(cardDTO);
    }


    @ParameterizedTest
    @MethodSource("invalidCardData")
    @DisplayName("test Save New Card With Invalid CardDTO")
    void testSaveNewCard_WithInvalidCardDTO_ThrowsException(CardDTO invalidCardDTO) {

        assertThrows(IllegalArgumentException.class, () -> cardController.saveNewCard(invalidCardDTO));

    }


    private static Stream<CardDTO> invalidCardData() {

        CardDTO invalidCardDTO1 = new CardDTO();
        invalidCardDTO1.setId(1l); // Invalid, id must be null

        // Invalid, because don't set nameCard
        CardDTO invalidCardDTO2 = new CardDTO();

        // Invalid, because set wrong nameCard
        CardDTO invalidCardDTO3 = new CardDTO();
        invalidCardDTO3.setNameCard("54");

        // Invalid, because set wrong nameCard
        CardDTO invalidCardDTO4 = new CardDTO();
        invalidCardDTO4.setNameCard("xx");

        // Invalid, because set wrong nameCard
        CardDTO invalidCardDTO5 = new CardDTO();
        invalidCardDTO5.setNameCard("5sdjhds@4");

        // Invalid, because set wrong nameCard
        CardDTO invalidCardDTO6 = new CardDTO();
        invalidCardDTO6.setNameCard("545445555454454");

        // Invalid, cardType shouldn't be null
        CardDTO invalidCardDTO7 = new CardDTO();
        invalidCardDTO7.setNameCard("1111111111111111");


        //Invalid. because Person and id of Person should not be null
        CardDTO invalidCardDTO8 = new CardDTO();
        invalidCardDTO8.setNameCard("1111111111111111");
        invalidCardDTO8.setCardType(CardType.cash);


        //Invalid. because Person and id of Person should not be null
        CardDTO invalidCardDTO9 = new CardDTO();
        invalidCardDTO9.setNameCard("1111111111111111");
        invalidCardDTO9.setCardType(CardType.credit);
        PersonDTO invalidPersonDTO = new PersonDTO();
        invalidCardDTO9.setPerson(invalidPersonDTO);


        //Invalid. because Sender and id of Sender should not be null
        CardDTO invalidCardDTO10 = new CardDTO();
        invalidCardDTO10.setNameCard("1111111111111111");
        invalidCardDTO10.setCardType(CardType.credit);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1l);
        invalidCardDTO10.setPerson(personDTO);


        //Invalid. because Sender and id of Sender should not be null
        CardDTO invalidCardDTO11 = new CardDTO();
        invalidCardDTO11.setNameCard("1111111111111111");
        invalidCardDTO11.setCardType(CardType.cash);
        PersonDTO personDTO1 = new PersonDTO();
        personDTO1.setId(1l);
        invalidCardDTO11.setPerson(personDTO);
        SenderDTO invalidSenderDTO = new SenderDTO();
        invalidCardDTO11.setSender(invalidSenderDTO);


        return Stream.of(
                invalidCardDTO1,
                invalidCardDTO2,
                invalidCardDTO3,
                invalidCardDTO4,
                invalidCardDTO5,
                invalidCardDTO6,
                invalidCardDTO7,
                invalidCardDTO8,
                invalidCardDTO9,
                invalidCardDTO10,
                invalidCardDTO11

        );
    }

}
