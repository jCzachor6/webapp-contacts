package czachor.jakub.contacts.service;

import czachor.jakub.contacts.dao.ContactDao;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.asm.ContactAsm;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
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
    public ContactDTO findContactById(Long id) {
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
    public List<ContactDTO> findAllContacts() {
        List<Contact> contactList = contactDao.findAllContacts();
        if (contactList != null) {
            return new ContactAsm().toResources(contactList);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteContact(ContactDTO contactDTO) {
        Contact contact = contactDao.findContactById(contactDTO.getRId());
        if (contact != null) {
            contactDao.deleteContact(contact);
        } else {
            throw new ContactDoesNotExistException(
                    "Contact with id " +
                            contactDTO.getRId() +
                            " doesn't exist"
            );
        }
    }

    @Override
    public void addContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setAddress(contactDTO.getAddress());
        contact.setSurname(contactDTO.getSurname());
        contact.setName(contactDTO.getName());
        contactDao.addContact(contact);
    }

    @Override
    public ContactDTO editContact(ContactDTO contactDTO) {
        Contact contact = contactDao.findContactById(contactDTO.getRId());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setAddress(contactDTO.getAddress());
        contact.setSurname(contactDTO.getSurname());
        contact.setName(contactDTO.getName());
        contactDao.editContact(contact);
        return findContactById(contact.getId());
    }
}
