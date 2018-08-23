package czachor.jakub.contacts.controller;

import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.service.ContactService;
import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Jakub Czachor
 * ContactController consist all REST methods used to manage contacts.
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * @param id - Unique identifier of a Comment
     * @return ContactDTO of given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContactDTO> getContactById(@PathVariable("id") Long id){
        try{
            ContactDTO contactDTO = contactService.findContactById(id);
            return new ResponseEntity<>(contactDTO, HttpStatus.FOUND);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return List of all contacts.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContactDTO>> getAllContacts(){
        List<ContactDTO> contactDTOList = contactService.findAllContacts();
        return new ResponseEntity<>(contactDTOList, HttpStatus.OK);
    }

    /**
     * @param contactDTO - resource to delete from database
     * @return HttpStatus OK if deleted, NOT_FOUND if couldn't find entity to delete
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@RequestBody ContactDTO contactDTO){
        try{
            contactService.deleteContact(contactDTO);
            return new ResponseEntity(HttpStatus.OK);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param contactDTO - resource to add to database
     * @return HttpStatus CREATED if added, NOT_FOUND if couldn't get entity back from database.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO){
        try {
            contactService.addContact(contactDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param contactDTO - resouce to update in database
     * @return HttpStatus OK if edited, NOT_FOUND if couldn't find entity to edit
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO){
        try {
            ContactDTO updated = contactService.editContact(contactDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
