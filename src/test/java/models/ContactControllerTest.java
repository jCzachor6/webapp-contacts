package models;

import czachor.jakub.contacts.controller.ContactController;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.asm.ContactAsm;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.service.ContactService;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactControllerTest {
    @InjectMocks
    private ContactController controller;

    @Mock
    private ContactService service;

    private MockMvc mockMvc;

    private Contact contact;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        contact = new Contact();
        contact.setId(1L);
        contact.setName("Name");
        contact.setSurname("Surname");
        contact.setAddress("Contact Address");
        contact.setPhoneNumber("100 200 300");
    }

    @Test
    public void findContactByIdTest() throws Exception {
        when(service.findContactById(1L)).thenReturn(new ContactAsm().toResource(contact));
        mockMvc.perform(get("/contact/{id}", 1L))
                .andExpect(status().isFound())
                //.andDo(print())
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.surname", is("Surname")))
                .andExpect(jsonPath("$.address", is("Contact Address")))
                .andExpect(jsonPath("$.phoneNumber", is("100 200 300")))
                .andExpect(jsonPath("$.rid", is(1)))
                .andExpect(jsonPath("$.links").isArray())
                .andExpect(jsonPath("$.links[0].href", containsString("/contact/1")));
    }

    @Test
    public void findNonExistingContactTest() throws Exception{
        when(service.findContactById(1L)).thenThrow(new ContactDoesNotExistException());
        mockMvc.perform(get("/contact/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllContactsTest() throws Exception {
        List<ContactDTO> contactDTOList = new ArrayList<>();
        contactDTOList.add(new ContactAsm().toResource(contact));
        contactDTOList.add(new ContactAsm().toResource(contact));
        contactDTOList.add(new ContactAsm().toResource(contact));
        when(service.findAllContacts()).thenReturn(contactDTOList);
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(jsonPath("$.*").isArray());
    }

}






























