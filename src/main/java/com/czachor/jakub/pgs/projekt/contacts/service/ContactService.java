package com.czachor.jakub.pgs.projekt.contacts.service;

import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;

import java.util.List;

public interface ContactService {
    ContactRes findContactById(Long id);
    List<ContactRes> findAllContacts();
    void deleteContact(ContactRes contactRes);
    void addContact(ContactRes contactRes);
    ContactRes editContact(ContactRes contactRes);
}
