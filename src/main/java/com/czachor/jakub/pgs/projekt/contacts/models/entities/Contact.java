package com.czachor.jakub.pgs.projekt.contacts.models.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Jakub Czachor
 * Contact class is used to store contacts in database.
 */
@Entity
@Table(name = "CONTACT")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @Size(max = 30)
    private String name;

    @Column(name = "SURNAME")
    @Size(max = 30)
    private String surname;

    @Column(name = "ADDRESS")
    @Size(max = 30)
    private String address;

    @Column(name = "PHONE_NUMBER")
    @Size(max = 15)
    private String phoneNumber;
}
