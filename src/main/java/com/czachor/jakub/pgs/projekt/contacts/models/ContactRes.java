package com.czachor.jakub.pgs.projekt.contacts.models;

import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ContactRes extends ResourceSupport{
    private Long rId;

    private String name;

    private String surname;

    private String address;

    private String phoneNumber;

    public ContactRes(Contact contact) {
        this.rId = contact.getId();
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.address = contact.getAddress();
        this.phoneNumber = contact.getPhoneNumber();
    }
}
