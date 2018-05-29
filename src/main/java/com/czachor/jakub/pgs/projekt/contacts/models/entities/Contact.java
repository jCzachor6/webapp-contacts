package com.czachor.jakub.pgs.projekt.contacts.models.entities;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "CONTACT")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
