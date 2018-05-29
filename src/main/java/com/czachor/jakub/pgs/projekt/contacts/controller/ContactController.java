package com.czachor.jakub.pgs.projekt.contacts.controller;

import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.service.ContactService;
import com.czachor.jakub.pgs.projekt.contacts.service.exceptions.ContactDoesNotExistException;
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
     * @return ContactRes of given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContactRes> getContactById(@PathVariable("id") Long id){
        try{
            ContactRes contactRes = contactService.findContactById(id);
            return new ResponseEntity<>(contactRes, HttpStatus.FOUND);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * @return List of all contacts.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContactRes>> getAllContacts(){
        List<ContactRes> contactResList = contactService.findAllContacts();
        return new ResponseEntity<>(contactResList, HttpStatus.OK);
    }

    /**
     * @param contactRes - resource to delete from database
     * @return HttpStatus OK if deleted, NO_CONTENT if couldn't find entity to delete
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@RequestBody ContactRes contactRes){
        try{
            contactService.deleteContact(contactRes);
            return new ResponseEntity(HttpStatus.OK);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * @param contactRes - resource to add to database
     * @return HttpStatus CREATED if added, NO_CONTENT if couldn't get entity back from database.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContactRes> createContact(@RequestBody ContactRes contactRes){
        try {
            contactService.addContact(contactRes);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * @param contactRes - resouce to update in database
     * @return HttpStatus OK if edited, NO_CONTENT if couldn't find entity to edit
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ContactRes> updateContact(@RequestBody ContactRes contactRes){
        try {
            ContactRes updated = contactService.editContact(contactRes);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
