package org.management.bank.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.management.domain.Card;
import org.management.domain.Person;
import org.management.domain.Sender;
import org.management.enums.CardType;
import org.management.repository.CardRepository;
import org.management.repository.PersonRepository;
import org.management.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CardRepositoryTest {


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SenderRepository senderRepository;


    @Test
    @DisplayName("test ExistsCardBySenderIdAndPersonId With Existing SenderId And PersonId")
    void testExistsCardBySenderIdAndPersonId_WithExistingSenderIdAndPersonId_ReturnsTrue() {
        //Arrange
        Long senderId = 1l;
        Long personId = 1l;

        //Act
        boolean exists = cardRepository.existsCardBySenderIdAndPersonId(senderId, personId);

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("test ExistsCardBySenderIdAndPersonId With Non Existing SenderId And PersonId")
    void testExistsCardBySenderIdAndPersonId_WithNonExistingSenderIdAndPersonId_ReturnsFalse() {
        //Arrange
        Long senderId = 3l;
        Long personId = 1l;

        //Act
        boolean exists = cardRepository.existsCardBySenderIdAndPersonId(senderId, personId);

        // Assert
        assertFalse(exists);
    }

    @Test
    @DisplayName("test ExistsByNameCard With Existing NameCard")
    void testExistsByNameCard_WithExistingNameCard_ReturnTrue() {

        //Arrange
        String nameCard = "6037709965412357";

        //Act
        boolean exists = cardRepository.existsByNameCard(nameCard);

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("test ExistsByNameCard With Non Existing NameCard")
    void testExistsByNameCard_WithNonExistingNameCard_ReturnFalse() {

        //Arrange
        String nameCard = "895412";

        //Act
        boolean exists = cardRepository.existsByNameCard(nameCard);

        // Assert
        assertFalse(exists);
    }

    @Test
    @DisplayName("test FindAllByPersonNationalCode")
    void testFindAllByPersonNationalCode_ReturnsCard() {


        //Arrange
        String nationalCode = "9654587454";

        Card card = new Card();
        card.setId(1l);
        card.setNameCard("6037996587436521");
        card.setCardType(CardType.cash);
        // 2028-12-31
        int year = 2028;
        int month = 12; // Note: months are zero-based (0 = January, 1 = February, ...)
        int day = 31;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();

        card.setExpirationDate(date);
        card.setStatus(true);

        Person person = personRepository.findById(1l).orElse(null);
        Sender sender = senderRepository.findById(1l).orElse(null);

        card.setPerson(person);
        card.setSender(sender);


        //Act
        List<Card> cardsActual = cardRepository.findAllByPersonNationalCode(nationalCode);

        // Assert
        assertEquals(card.getId(), cardsActual.get(0).getId());
        assertEquals(card.getNameCard(), cardsActual.get(0).getNameCard());
        assertEquals(card.getCardType(), cardsActual.get(0).getCardType());

        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd  z yyyy");
        String expectedDateString = dateFormat.format(card.getExpirationDate());
        String actualDateString = dateFormat.format(cardsActual.get(0).getExpirationDate());
        assertEquals(expectedDateString, actualDateString);

        assertEquals(card.getStatus(), cardsActual.get(0).getStatus());
        assertEquals(card.getPerson().getId(), cardsActual.get(0).getPerson().getId());
        assertEquals(card.getSender().getId(), cardsActual.get(0).getSender().getId());
        assertEquals(card.getSender(), cardsActual.get(0).getSender());
        assertEquals(card.getPerson(), cardsActual.get(0).getPerson());

    }

    @Test
    @DisplayName("test FindAllByPersonNationalCode with invalid nationalCode")
    void testFindAllByPersonNationalCode_ReturnsEmptyList() {

        //Arrange
        String nationalCode = "12356";

        //Act
        List<Card> cardsActual = cardRepository.findAllByPersonNationalCode(nationalCode);
        List<Card> cardsExpected = new ArrayList<>();

        // Assert
        assertEquals(cardsExpected, cardsActual);

    }
}
