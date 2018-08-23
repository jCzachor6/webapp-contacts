package dao;

import czachor.jakub.contacts.config.HibernateConfig;
import czachor.jakub.contacts.dao.ContactDao;
import czachor.jakub.contacts.models.entities.Contact;
import models.ContactTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
@WebAppConfiguration
public class ContactDaoTest {

    @Autowired
    private ContactDao contactDao;

    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
    }

    @Test
    @Transactional
    public void getContactFromDbTest() {
        Contact contact = ContactTest.getSampleInstance();
        Contact added = contactDao.addContact(contact);
        Contact found = contactDao.findContactById(added.getId());
        assertNotNull(found);
    }

    @Test
    @Transactional
    public void getAllContactsFromDbTest() {
        Contact contact = ContactTest.getSampleInstance();
        Contact added1 = contactDao.addContact(contact);
        Contact added2 = contactDao.addContact(contact);
        Contact added3 = contactDao.addContact(contact);
        List<Contact> contacts = contactDao.findAllContacts();
        assertNotNull(contacts);
        assertEquals(3, contacts.size());
    }

    @Test
    @Transactional
    public void getAllContactsFromDbWhenEmpty() {
        List<Contact> contacts = contactDao.findAllContacts();
        assertNotNull(contacts);
        assertTrue(contacts.isEmpty());
        assertEquals(0, contacts.size());
    }

    @Test
    @Transactional
    public void deleteContactTest() {
        Contact contact = ContactTest.getSampleInstance();
        Contact toDelete = contactDao.addContact(contact);
        contactDao.deleteContact(toDelete);
        Contact deleted = contactDao.findContactById(toDelete.getId());
        assertNull(deleted);
    }

    @Test
    @Transactional
    public void updateContactTest(){
        Contact contact = ContactTest.getSampleInstance();
        Contact toUpdate = contactDao.addContact(contact);
        toUpdate.setName("AnotherName");
        contactDao.editContact(toUpdate);
        Contact updated = contactDao.findContactById(toUpdate.getId());
        assertEquals("AnotherName", updated.getName());
    }
}
