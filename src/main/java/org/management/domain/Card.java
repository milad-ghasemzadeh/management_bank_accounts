package org.management.domain;

import lombok.Getter;
import lombok.Setter;
import org.management.enums.CardType;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nameCard;

    @Enumerated(EnumType.ORDINAL)
    private CardType cardType;

    private Date expirationDate;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender sender;

}
