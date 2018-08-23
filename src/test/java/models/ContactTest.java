package models;

import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.utils.ContactMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ContactTest {
    private Contact contact;

    @Before
    public void setup() {
        contact = getSampleInstance();
    }

    public static Contact getSampleInstance() {
        Contact contact = new Contact();
        contact.setId(0L);
        contact.setName("Name");
        contact.setSurname("Surname");
        contact.setAddress("Address");
        contact.setPhoneNumber("202 202 202");
        return contact;
    }

    @Test
    public void verifyContactResConstructor() {
        ContactDTO contactDTO = new ContactMapper().map(contact);
        assertEquals(contactDTO.getAddress(), contact.getAddress());
        assertEquals(contactDTO.getName(), contact.getName());
        assertEquals(contactDTO.getPhoneNumber(), contact.getPhoneNumber());
        assertEquals(contactDTO.getRId(), contact.getId());
        assertEquals(contactDTO.getSurname(), contact.getSurname());
    }

}
