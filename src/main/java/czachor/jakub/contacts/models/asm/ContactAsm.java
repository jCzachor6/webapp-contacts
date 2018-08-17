package czachor.jakub.contacts.models.asm;

import czachor.jakub.contacts.controller.ContactController;
import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.entities.Contact;
import czachor.jakub.contacts.utils.ContactMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jakub Czachor
 * Class used to set additional links to contactRes object.
 */
public class ContactAsm extends ResourceAssemblerSupport<Contact, ContactDTO> {
    private ContactMapper mapper;
    public ContactAsm(){
        super(ContactController.class, ContactDTO.class);
        mapper = new ContactMapper();
    }


    @Override
    public ContactDTO toResource(Contact contact) {
        ContactDTO contactDTO = mapper.map(contact);
        contactDTO.add(
                linkTo(methodOn(ContactController.class).getContactById(contact.getId())).withSelfRel()
        );
        return contactDTO;
    }

    @Override
    public List<ContactDTO> toResources(Iterable<? extends Contact> entities) {
        return super.toResources(entities);
    }
}
