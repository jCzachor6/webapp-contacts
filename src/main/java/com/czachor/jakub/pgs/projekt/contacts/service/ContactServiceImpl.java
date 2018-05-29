package com.czachor.jakub.pgs.projekt.contacts.service;

import com.czachor.jakub.pgs.projekt.contacts.dao.ContactDao;
import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.models.asm.ContactAsm;
import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import com.czachor.jakub.pgs.projekt.contacts.service.exceptions.ContactDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao;

    @Autowired
    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public ContactRes findContactById(Long id) {
        Contact contact = contactDao.findContactById(id);
        if (contact != null) {
            return new ContactAsm().toResource(contact);
        } else {
            throw new ContactDoesNotExistException(
                    "Contact with id " +
                            id +
                            " doesn't exist"
            );
        }
    }

    @Override
    public List<ContactRes> findAllContacts() {
        List<Contact> contactList = contactDao.findAllContacts();
        if (contactList != null) {
            return new ContactAsm().toResources(contactList);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteContact(ContactRes contactRes) {
        Contact contact = contactDao.findContactById(contactRes.getRId());
        if (contact != null) {
            contactDao.deleteContact(contact);
        } else {
            throw new ContactDoesNotExistException(
                    "Contact with id " +
                            contactRes.getRId() +
                            " doesn't exist"
            );
        }
    }

    @Override
    public ContactRes addContact(ContactRes contactRes) {
        Contact contact = new Contact();
        contact.setPhoneNumber(contactRes.getPhoneNumber());
        contact.setAddress(contactRes.getAddress());
        contact.setSurname(contactRes.getSurname());
        contact.setName(contactRes.getName());
        contactDao.addContact(contact);
        return findContactById(contact.getId());
    }

    @Override
    public ContactRes editContact(ContactRes contactRes) {
        Contact contact = new Contact();
        contact.setId(contactRes.getRId());
        contact.setPhoneNumber(contactRes.getPhoneNumber());
        contact.setAddress(contactRes.getAddress());
        contact.setSurname(contactRes.getSurname());
        contact.setName(contactRes.getName());
        contactDao.editContact(contact);
        return findContactById(contact.getId());
    }
}
