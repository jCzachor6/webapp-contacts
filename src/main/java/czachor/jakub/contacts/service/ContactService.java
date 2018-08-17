package czachor.jakub.contacts.service;

import czachor.jakub.contacts.models.ContactDTO;

import java.util.List;

public interface ContactService {
    ContactDTO findContactById(Long id);
    List<ContactDTO> findAllContacts();
    void deleteContact(ContactDTO contactDTO);
    void addContact(ContactDTO contactDTO);
    ContactDTO editContact(ContactDTO contactDTO);
}
