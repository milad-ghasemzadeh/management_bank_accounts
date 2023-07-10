package org.management.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String familyName;

    private String address;

    @Column(unique = true)
    private String nationalCode;

    private String phoneNumber;

}
