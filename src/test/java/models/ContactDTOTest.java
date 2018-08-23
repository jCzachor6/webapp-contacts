package models;

import czachor.jakub.contacts.models.ContactDTO;

public class ContactDTOTest {
    public static ContactDTO getSampleInstance(){
        ContactDTO dto = new ContactDTO();
        dto.setRId(0L);
        dto.setName("Name");
        dto.setSurname("Surname");
        dto.setAddress("Address");
        dto.setPhoneNumber("202 202 202");
        return dto;
    }
}
