package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import czachor.jakub.contacts.controller.AppControllerAdvice;
import czachor.jakub.contacts.controller.ContactController;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.asm.ContactAsm;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.service.ContactService;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import czachor.jakub.contacts.utils.ContactMapper;
import models.ContactDTOTest;
import models.ContactTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new AppControllerAdvice())
                .build();
        contact = ContactTest.getSampleInstance();
    }

    @Test
    public void findContactByIdTest() throws Exception {
        when(service.findContactById(1L)).thenReturn(new ContactAsm(new ContactMapper()).toResource(contact));
        mockMvc.perform(get("/contact/{id}", 1L))
                .andExpect(status().isFound())
                //.andDo(print())
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.surname", is("Surname")))
                .andExpect(jsonPath("$.address", is("Address")))
                .andExpect(jsonPath("$.phoneNumber", is("202 202 202")))
                .andExpect(jsonPath("$.rid", is(0)))
                .andExpect(jsonPath("$.links").isArray())
                .andExpect(jsonPath("$.links[0].href", containsString("/contact/0")));
    }

    @Test
    public void findNonExistingContactTest() throws Exception {
        when(service.findContactById(1L)).thenThrow(new ContactDoesNotExistException());
        mockMvc.perform(get("/contact/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllContactsTest() throws Exception {
        List<ContactDTO> contactDTOList = new ArrayList<>();
        contactDTOList.add(new ContactAsm(new ContactMapper()).toResource(contact));
        contactDTOList.add(new ContactAsm(new ContactMapper()).toResource(contact));
        contactDTOList.add(new ContactAsm(new ContactMapper()).toResource(contact));
        when(service.findAllContacts()).thenReturn(contactDTOList);
        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray());
    }

    @Test
    public void deleteContactTest() throws Exception {
        ContactDTO deletedContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(deletedContact);

        mockMvc.perform(delete("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteContact(deletedContact);
    }

    @Test
    public void deleteContactExceptionTest() throws Exception {
        ContactDTO deletedContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(deletedContact);

        doThrow(new ContactDoesNotExistException()).when(service).deleteContact(deletedContact);

        mockMvc.perform(delete("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());
        verify(service, times(1)).deleteContact(deletedContact);
    }

    @Test
    public void createContactTest() throws Exception {
        ContactDTO createdContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(createdContact);

        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());
        verify(service, times(1)).addContact(createdContact);
    }

    @Test
    public void createContactExceptionTest() throws Exception {
        ContactDTO createdContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(createdContact);

        doThrow(new ContactDoesNotExistException()).when(service).addContact(createdContact);

        mockMvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());
        verify(service, times(1)).addContact(createdContact);
    }

    @Test
    public void updateContactTest() throws Exception {
        ContactDTO updatedContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(updatedContact);
        when(service.editContact(updatedContact)).thenReturn(new ContactAsm(new ContactMapper()).toResource(contact));

        mockMvc.perform(put("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Name")))
                .andExpect(jsonPath("$.surname", is("Surname")))
                .andExpect(jsonPath("$.address", is("Address")))
                .andExpect(jsonPath("$.phoneNumber", is("202 202 202")))
                .andExpect(jsonPath("$.rid", is(0)))
                .andExpect(jsonPath("$.links").isArray())
                .andExpect(jsonPath("$.links[0].href", containsString("/contact/0")));
        verify(service, times(1)).editContact(updatedContact);
    }

    @Test
    public void updateContactExceptionTest() throws Exception {
        ContactDTO updatedContact = ContactDTOTest.getSampleInstance();
        String requestJson = getJson(updatedContact);

        doThrow(new ContactDoesNotExistException()).when(service).editContact(updatedContact);

        mockMvc.perform(put("/contact")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());
        verify(service, times(1)).editContact(updatedContact);
    }

    private String getJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}






























