package com.czachor.jakub.pgs.projekt.contacts.models.asm;

import com.czachor.jakub.pgs.projekt.contacts.controller.ContactController;
import com.czachor.jakub.pgs.projekt.contacts.models.ContactRes;
import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

public class ContactAsm extends ResourceAssemblerSupport<Contact, ContactRes> {
    public ContactAsm(){
        super(ContactController.class, ContactRes.class);
    }


    @Override
    public ContactRes toResource(Contact contact) {
        ContactRes contactRes = new ContactRes(contact);

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
