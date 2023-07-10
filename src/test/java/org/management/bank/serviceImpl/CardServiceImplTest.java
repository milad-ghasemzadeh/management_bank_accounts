package org.management.bank.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.management.domain.Card;
import org.management.domain.Sender;
import org.management.dto.CardDTO;
import org.management.dto.PersonDTO;
import org.management.dto.SenderDTO;
import org.management.exception.CustomException;
import org.management.mapper.CardMapper;
import org.management.repository.CardRepository;
import org.management.repository.PersonRepository;
import org.management.repository.SenderRepository;
import org.management.service.impl.CardServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private SenderRepository senderRepository;
    @Mock
    private PersonRepository personRepository;

    @Autowired
    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardServiceImpl(cardRepository, cardMapper, senderRepository, personRepository);
    }

    @Test
    @DisplayName("test GetCards With Valid NationalCode")
    void testGetCards_WithValidNationalCode_ReturnsListOfCards() {
        // Arrange
        String nationalCode = "1029674125";
        List<Card> cards = new ArrayList<>();
        Card card = new Card();
        card.setNameCard("6276481112589741");
        cards.add(card);

        // Mocking the repository behavior
        when(cardRepository.findAllByPersonNationalCode(eq(nationalCode))).thenReturn(cards);
        when(cardMapper.toDto(any(Card.class))).thenReturn(new CardDTO());

        // Act
        List<CardDTO> result = cardService.getCards(nationalCode);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(cardRepository).findAllByPersonNationalCode(eq(nationalCode));
        verify(cardMapper, times(cards.size())).toDto(any(Card.class));
    }

    @Test
    @DisplayName("test Get Cards With Invalid NationalCode")
    void testGetCards_WithInvalidNationalCode_ThrowsCustomException() {
        // Arrange
        String nationalCode = "1111111111";

        when(cardRepository.findAllByPersonNationalCode(nationalCode)).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.getCards(nationalCode));
    }

    @Test
    @DisplayName("test Save With Valid CardDTO")
    void testSave_WithValidCardDTO_ReturnsSavedCardDTO() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNameCard("6276481111111111");
        SenderDTO senderDTO = new SenderDTO();
        senderDTO.setId(2l);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(5l);
        cardDTO.setSender(senderDTO);
        cardDTO.setPerson(personDTO);

        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(true);
        Sender sender = new Sender();
        sender.setSenderCode("627648"); // Set a valid sender code
        when(senderRepository.findById(cardDTO.getSender().getId())).thenReturn(Optional.of(sender));

        when(cardRepository.existsCardBySenderIdAndPersonId(cardDTO.getSender().getId(), cardDTO.getPerson().getId())).thenReturn(false);
        when(cardRepository.existsByNameCard(cardDTO.getNameCard())).thenReturn(false);
        when(cardMapper.toEntity(cardDTO)).thenReturn(new Card());
        when(cardRepository.save(any(Card.class))).thenReturn(new Card());
        when(cardMapper.toDto(any(Card.class))).thenReturn(cardDTO);

        // Act
        CardDTO result = cardService.save(cardDTO);

        // Assert
        assertNotNull(result);
        assertEquals(cardDTO, result);
        verify(personRepository).existsById(cardDTO.getPerson().getId());
        verify(senderRepository).findById(cardDTO.getSender().getId());
        verify(cardRepository).existsCardBySenderIdAndPersonId(cardDTO.getSender().getId(), cardDTO.getPerson().getId());
        verify(cardRepository).existsByNameCard(cardDTO.getNameCard());
        verify(cardRepository).save(any(Card.class));
        verify(cardMapper).toDto(any(Card.class));
    }

    @Test
    @DisplayName("test Save With Non Existing PersonId")
    void testSave_WithNonExistingPersonId_ThrowsCustomException() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setPerson(new PersonDTO());
        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(false);

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.save(cardDTO));
    }


    @Test
    @DisplayName("test Save With Non Existing Sender")
    void testSave_WithNonExistingSender_ThrowsCustomException() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setSender(new SenderDTO());
        cardDTO.setPerson(new PersonDTO());
        cardDTO.getSender().setId(1L);
        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(true);
        when(senderRepository.findById(cardDTO.getSender().getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.save(cardDTO));
    }

    @Test
    @DisplayName("test Save With Non Match Six Digits SenderCode")
    void testSave_WithNonMatchSixDigitsSenderCode_ThrowsCustomException() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNameCard("3533331111111111");
        SenderDTO senderDTO = new SenderDTO();
        senderDTO.setId(2l);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(5l);
        cardDTO.setSender(senderDTO);
        cardDTO.setPerson(personDTO);

        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(true);

        Sender sender = new Sender();
        sender.setSenderCode("627648"); // Set a valid sender code
        when(senderRepository.findById(cardDTO.getSender().getId())).thenReturn(Optional.of(sender));

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.save(cardDTO));
    }


    @Test
    @DisplayName("test Save With Existing Sender And Person")
    void testSave_WithExistingSenderAndPerson_ThrowsCustomException() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNameCard("6276481111111111");
        SenderDTO senderDTO = new SenderDTO();
        senderDTO.setId(2l);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(5l);
        cardDTO.setSender(senderDTO);
        cardDTO.setPerson(personDTO);

        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(true);
        Sender sender = new Sender();
        sender.setSenderCode("627648"); // Set a valid sender code
        when(senderRepository.findById(cardDTO.getSender().getId())).thenReturn(Optional.of(sender));
        when(cardRepository.existsCardBySenderIdAndPersonId(cardDTO.getSender().getId(), cardDTO.getPerson().getId())).thenReturn(true);

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.save(cardDTO));
    }

    @Test
    @DisplayName("test Save With Existing NameCard")
    void testSave_WithExistingNameCard_ThrowsCustomException() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        cardDTO.setNameCard("6276481111111111");
        SenderDTO senderDTO = new SenderDTO();
        senderDTO.setId(2l);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(5l);
        cardDTO.setSender(senderDTO);
        cardDTO.setPerson(personDTO);

        when(personRepository.existsById(cardDTO.getPerson().getId())).thenReturn(true);
        Sender sender = new Sender();
        sender.setSenderCode("627648"); // Set a valid sender code
        when(senderRepository.findById(cardDTO.getSender().getId())).thenReturn(Optional.of(sender));
        when(cardRepository.existsCardBySenderIdAndPersonId(cardDTO.getSender().getId(), cardDTO.getPerson().getId())).thenReturn(false);
        when(cardRepository.existsByNameCard(cardDTO.getNameCard())).thenReturn(true);

        // Act and Assert
        assertThrows(CustomException.class, () -> cardService.save(cardDTO));
    }
}
