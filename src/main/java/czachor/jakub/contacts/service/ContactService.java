package czachor.jakub.contacts.service;

import czachor.jakub.contacts.models.ContactDTO;

import java.util.List;

public interface ContactService {
    ContactDTO findContactById(Long id);
    List<ContactDTO> findAllContacts();
    boolean deleteContact(ContactDTO contactDTO);
    ContactDTO addContact(ContactDTO contactDTO);
    ContactDTO editContact(ContactDTO contactDTO);
}
