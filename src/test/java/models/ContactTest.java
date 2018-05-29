package models;

import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ContactTest {
    private Contact contact;

    @Before
    public void setup(){
        contact = new Contact();
        contact.setId(0L);
        contact.setName("Name");
        contact.setSurname("Surname");
        contact.setAddress("Address");
        contact.setPhoneNumber("202 202 202");
    }


    @Test
    public void verifyContactResConstructor(){
        ContactRes contactRes = new ContactRes(contact);
        assertEquals(contactRes.getAddress(), contact.getAddress());
        assertEquals(contactRes.getName(), contact.getName());
        assertEquals(contactRes.getPhoneNumber(), contact.getPhoneNumber());
        assertEquals(contactRes.getRId(), contact.getId());
        assertEquals(contactRes.getSurname(), contact.getSurname());
    }

    @Test
    public void verifyNoLinksInContactResYet(){
        ContactRes contactRes = new ContactRes(contact);
        assertFalse(contactRes.hasLinks());
    }
}
