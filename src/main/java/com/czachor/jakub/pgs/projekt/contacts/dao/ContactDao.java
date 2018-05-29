package com.czachor.jakub.pgs.projekt.contacts.dao;

import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;

import java.util.List;

public interface ContactDao {
    Contact findContactById(Long id);
    List findAllContacts();
    void deleteContact(Contact contact);
    void addContact(Contact contact);
    void editContact(Contact contact);
}
