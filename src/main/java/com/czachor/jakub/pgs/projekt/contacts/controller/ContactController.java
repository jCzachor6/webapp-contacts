package com.czachor.jakub.pgs.projekt.contacts.controller;

import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.service.ContactService;
import com.czachor.jakub.pgs.projekt.contacts.service.exceptions.ContactDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContactRes> getContactById(@PathVariable("id") Long id){
        try{
            ContactRes contactRes = contactService.findContactById(id);
            return new ResponseEntity<>(contactRes, HttpStatus.FOUND);
        }catch(ContactDoesNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContactRes>> getAllContacts(){
        List<ContactRes> contactResList = contactService.findAllContacts();
        return new ResponseEntity<>(contactResList, HttpStatus.OK);
    }
}
