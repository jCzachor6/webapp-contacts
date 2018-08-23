package service;

import czachor.jakub.contacts.dao.ContactDao;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.service.ContactService;
import czachor.jakub.contacts.service.ContactServiceImpl;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import czachor.jakub.contacts.utils.ContactMapper;
import models.ContactDTOTest;
import models.ContactTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ContactServiceTest {
    @Configuration
    static class ContactServiceTestContextConfiguration{
        @Bean
        public ContactService contactService(){
            return new ContactServiceImpl(contactDao());
        }
        @Bean
        public ContactDao contactDao(){
            return Mockito.mock(ContactDao.class);
        }
    }

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactDao contactDao;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findContactByIdTest() {
        Contact foundContact = ContactTest.getSampleInstance();
        when(contactDao.findContactById(0L)).thenReturn(foundContact);
        ContactDTO contactDTO = contactService.findContactById(0L);
        assertEquals(foundContact.getId(), contactDTO.getRId());
        assertTrue(contactDTO.hasLinks());
        assertTrue(contactDTO.hasLink("self"));
    }

    @Test
    public void findContactByIdNotFoundTest() {
        try {
            when(contactDao.findContactById(0L)).thenReturn(null);
            ContactDTO contactDTO = contactService.findContactById(0L);
            fail();
        }catch (ContactDoesNotExistException e){
            assertEquals("Contact with id 0 doesn't exist", e.getMessage());
        }
    }

    @Test
    public void findAllContactsTest() {
        when(contactDao.findAllContacts()).thenReturn(Arrays.asList(
                ContactTest.getSampleInstance(),
                ContactTest.getSampleInstance(),
                ContactTest.getSampleInstance()
        ));
        List<ContactDTO> contactDTOList = contactService.findAllContacts();
        assertEquals(3, contactDTOList.size());
        contactDTOList.forEach((s)->assertTrue(s.hasLinks()));
    }

    @Test
    public void findAllContactsWhenEmptyTest() {
        when(contactDao.findAllContacts()).thenReturn(Collections.emptyList());
        List<ContactDTO> contactDTOList = contactService.findAllContacts();
        assertEquals(0, contactDTOList.size());
        assertNotNull(contactDTOList);
    }

    @Test
    public void addContactTest() {
        ContactDTO addedContactDTO = ContactDTOTest.getSampleInstance();
        Contact addedContact = new ContactMapper().map(addedContactDTO);
        when(contactDao.addContact(addedContact)).thenReturn(addedContact);
        assertNotNull(contactService.addContact(addedContactDTO));
        verify(contactDao, times(1)).addContact(addedContact);
    }

    @Test
    public void deleteContactTest(){
        ContactDTO contactDTO = ContactDTOTest.getSampleInstance();
        Contact contact = new ContactMapper().map(contactDTO);
        when(contactDao.findContactById(contactDTO.getRId())).thenReturn(contact);
        assertTrue(contactService.deleteContact(contactDTO));
        verify(contactDao, times(1)).deleteContact(contact);
    }

    @Test
    public void deleteNonExistingContactTest(){
        try {
            ContactDTO contactDTO = ContactDTOTest.getSampleInstance();
            when(contactDao.findContactById(contactDTO.getRId())).thenReturn(null);
            contactService.deleteContact(contactDTO);
            fail();
        }catch (ContactDoesNotExistException e){
            assertEquals("Contact with id 0 doesn't exist", e.getMessage());
        }
    }

    @Test
    public void editContactTest(){
        ContactDTO contactDTO = ContactDTOTest.getSampleInstance();
        Contact contact = new ContactMapper().map(contactDTO);
        when(contactDao.findContactById(contact.getId())).thenReturn(contact);
        assertNotNull(contactService.editContact(contactDTO));
        verify(contactDao, times(1)).editContact(contact);
    }

    @Test
    public void editNonExistingContactTest(){
        try {
            ContactDTO contactDTO = ContactDTOTest.getSampleInstance();
            when(contactDao.findContactById(contactDTO.getRId())).thenReturn(null);
            contactService.editContact(contactDTO);
            fail();
        }catch (ContactDoesNotExistException e){
            assertEquals("Contact with id 0 doesn't exist", e.getMessage());
        }
    }
}
