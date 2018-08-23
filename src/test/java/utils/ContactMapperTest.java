package utils;

import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.utils.ContactMapper;
import models.ContactDTOTest;
import models.ContactTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ContactMapperTest {
    private Contact contact;
    private ContactDTO contactDTO;
    private ContactMapper mapper;

    @Before
    public void setup(){
        contact = ContactTest.getSampleInstance();
        contactDTO = ContactDTOTest.getSampleInstance();
        mapper = new ContactMapper();
    }

    @Test
    public void ContactDTOtoContactMapTest(){
        Contact contact = mapper.map(contactDTO);
        assertEquals(contact.getId(), contactDTO.getRId());
        assertEquals(contact.getAddress(), contactDTO.getAddress());
        assertEquals(contact.getName(), contactDTO.getName());
        assertEquals(contact.getPhoneNumber(), contactDTO.getPhoneNumber());
        assertEquals(contact.getSurname(), contactDTO.getSurname());
    }

    @Test
    public void ContactToContactDTOMapTest(){
        ContactDTO dto = mapper.map(contact);
        assertEquals(contact.getId(), dto.getRId());
        assertEquals(contact.getAddress(), dto.getAddress());
        assertEquals(contact.getName(), dto.getName());
        assertEquals(contact.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(contact.getSurname(), dto.getSurname());
        assertFalse(dto.hasLinks());
    }

    @Test
    public void ContactToContactDTOWithLinksMapTest(){
        ContactDTO dto = mapper.mapWithLinks(contact);
        assertTrue(dto.hasLinks());
        assertTrue(dto.hasLink("self"));
    }

    @Test
    public void ContactListToContactDTOListWithLinksMapTest(){
        List<Contact> contactList = new ArrayList<>(Arrays.asList(
                ContactTest.getSampleInstance(),
                ContactTest.getSampleInstance(),
                ContactTest.getSampleInstance()
                ));
        List<ContactDTO> contactDTOList = mapper.mapWithLinks(contactList);
        for(ContactDTO c : contactDTOList){
            assertTrue(c.hasLinks());
            assertTrue(c.hasLink("self"));
        }
    }
}
