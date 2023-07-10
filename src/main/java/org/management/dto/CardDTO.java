package org.management.dto;

import lombok.Getter;
import lombok.Setter;
import org.management.enums.CardType;

import java.util.Date;

@Getter
@Setter
public class CardDTO {

    private Long id;

    private String nameCard;

    private CardType cardType;

    private Date expirationDate;

    private Boolean status;

    private PersonDTO person;

    private SenderDTO sender;

}
