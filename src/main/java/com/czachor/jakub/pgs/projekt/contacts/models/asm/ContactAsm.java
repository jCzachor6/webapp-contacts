package com.czachor.jakub.pgs.projekt.contacts.models.asm;

import com.czachor.jakub.pgs.projekt.contacts.controller.ContactController;
import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jakub Czachor
 * Class used to set additional links to contactRes object.
 */
public class ContactAsm extends ResourceAssemblerSupport<Contact, ContactRes> {
    public ContactAsm(){
        super(ContactController.class, ContactRes.class);
    }


    @Override
    public ContactRes toResource(Contact contact) {
        ContactRes contactRes = new ContactRes(contact);
        contactRes.add(
                linkTo(methodOn(ContactController.class).getContactById(contact.getId())).withSelfRel()
        );
        return contactRes;
    }

    @Override
    public List<ContactRes> toResources(Iterable<? extends Contact> entities) {
        List<ContactRes> contactResList = new ArrayList<>();
        for(Contact c : entities){
            contactResList.add(new ContactRes(c));
        }
        return super.toResources(entities);
    }
}
