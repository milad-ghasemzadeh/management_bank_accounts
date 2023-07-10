package org.management.service.impl;

import org.management.domain.Card;
import org.management.domain.Sender;
import org.management.dto.CardDTO;
import org.management.exception.CustomException;
import org.management.exception.ErrorMessageConstants;
import org.management.mapper.CardMapper;
import org.management.repository.CardRepository;
import org.management.repository.PersonRepository;
import org.management.repository.SenderRepository;
import org.management.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final SenderRepository senderRepository;
    private final PersonRepository personRepository;


    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper,
                           SenderRepository senderRepository, PersonRepository personRepository) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.senderRepository = senderRepository;
        this.personRepository = personRepository;
    }

    // get all cards for specific person that who has this nationalCode.
    @Override
    public List<CardDTO> getCards(String nationalCode) {

        List<Card> cards = cardRepository.findAllByPersonNationalCode(nationalCode);

        if (cards.size() == 0) {
            logger.error(ErrorMessageConstants.CouldNotFindPerson.developer_message);
            throw new CustomException(ErrorMessageConstants.CouldNotFindPerson.message,
                    ErrorMessageConstants.CouldNotFindPerson.developer_message,
                    ErrorMessageConstants.CouldNotFindPerson.status);
        }

        return cards.stream().map(cardMapper::toDto).collect(Collectors.toList());

    }

    //save the new Card
    @Override
    public CardDTO save(CardDTO cardDTO) {

        // check the information
        checkPersonId(cardDTO.getPerson().getId());
        checkSenderCodeCard(cardDTO.getNameCard(), cardDTO.getSender().getId());
        checkUniquesOfSenderCodeForPerson(cardDTO.getSender().getId(), cardDTO.getPerson().getId());
        checkUniquesNameCard(cardDTO.getNameCard());

        // set the expirationDate of card
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        Date futureDate = calendar.getTime();
        cardDTO.setExpirationDate(futureDate);

        // save card
        Card card = cardMapper.toEntity(cardDTO);
        // enable the card
        card.setStatus(true);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);

    }

    // check PersonId exists in Database.
    private void checkPersonId(Long personId) {
        if (!personRepository.existsById(personId)) {
            logger.error(ErrorMessageConstants.CouldNotFindPersonId.developer_message);
            throw new CustomException(ErrorMessageConstants.CouldNotFindPersonId.message,
                    ErrorMessageConstants.CouldNotFindPersonId.developer_message,
                    ErrorMessageConstants.CouldNotFindPersonId.status);
        }
    }

    // check SenderId exists in Database And check the First 6 Digits of nameCard.
    private void checkSenderCodeCard(String nameCard, Long senderId) {
        Sender sender = senderRepository.findById(senderId).orElse(null);
        if (sender == null) {
            logger.error(ErrorMessageConstants.CouldNotFindSenderId.developer_message);
            throw new CustomException(ErrorMessageConstants.CouldNotFindSenderId.message,
                    ErrorMessageConstants.CouldNotFindSenderId.developer_message,
                    ErrorMessageConstants.CouldNotFindSenderId.status);

        } else {
            // Check the six digits of nameCard, according to sender of Card,
            // the first 6 digits should be the same with senderCode
            String tempSixDigits = nameCard.substring(0, 6);
            if (!tempSixDigits.equals(sender.getSenderCode())) {
                logger.error(ErrorMessageConstants.FirstSixDigitCard.developer_message);
                throw new CustomException(ErrorMessageConstants.FirstSixDigitCard.message,
                        ErrorMessageConstants.FirstSixDigitCard.developer_message,
                        ErrorMessageConstants.FirstSixDigitCard.status);
            }
        }
    }

    // check the Uniques Of SenderCode For every Person
    private void checkUniquesOfSenderCodeForPerson(Long senderId, Long personId) {
        if (cardRepository.existsCardBySenderIdAndPersonId(senderId, personId)) {
            logger.error(ErrorMessageConstants.UniquesOfSenderCodeForPerson.developer_message);
            throw new CustomException(ErrorMessageConstants.UniquesOfSenderCodeForPerson.message,
                    ErrorMessageConstants.UniquesOfSenderCodeForPerson.developer_message,
                    ErrorMessageConstants.UniquesOfSenderCodeForPerson.status);
        }
    }

    // check the Uniques of nameCard.
    private void checkUniquesNameCard(String nameCard) {
        if (cardRepository.existsByNameCard(nameCard)) {
            logger.error(ErrorMessageConstants.UniquesNameCard.developer_message);
            throw new CustomException(ErrorMessageConstants.UniquesNameCard.message,
                    ErrorMessageConstants.UniquesNameCard.developer_message,
                    ErrorMessageConstants.UniquesNameCard.status);

        }
    }
}