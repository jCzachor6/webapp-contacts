package czachor.jakub.contacts.service;

import czachor.jakub.contacts.dao.ContactDao;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import czachor.jakub.contacts.utils.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao;

    private final ContactMapper contactMapper;

    @Autowired
    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
        this.contactMapper = new ContactMapper();
    }

    @Override
    public ContactDTO findContactById(Long id) {
        Contact contact = contactDao.findContactById(id);
        if (contact != null) {
            return contactMapper.mapWithLinks(contact);
        } else {
            throw new ContactDoesNotExistException("Contact with id " + id + " doesn't exist");
        }
    }

    @Override
    public List<ContactDTO> findAllContacts() {
        List<Contact> contactList = contactDao.findAllContacts();
        return contactMapper.mapWithLinks(contactList);
    }

    @Override
    public boolean deleteContact(ContactDTO contactDTO) {
        Contact contact = contactDao.findContactById(contactDTO.getRId());
        if (contact != null) {
            contactDao.deleteContact(contact);
            return true;
        } else {
            throw new ContactDoesNotExistException("Contact with id " + contactDTO.getRId() + " doesn't exist");
        }
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.map(contactDTO);
        Contact addedContact = contactDao.addContact(contact);
        return contactMapper.mapWithLinks(addedContact);
    }

    @Override
    public ContactDTO editContact(ContactDTO contactDTO) {
        Long contactId = contactDTO.getRId();
        Contact contact = contactDao.findContactById(contactId);
        if (contact != null) {
            contact = contactMapper.map(contactDTO);
            contactDao.editContact(contact);
            return findContactById(contactId);
        } else {
            throw new ContactDoesNotExistException("Contact with id " + contactDTO.getRId() + " doesn't exist");
        }
    }
}
