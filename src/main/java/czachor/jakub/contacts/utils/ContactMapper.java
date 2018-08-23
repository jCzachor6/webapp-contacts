package czachor.jakub.contacts.utils;

import czachor.jakub.contacts.models.ContactDTO;
import czachor.jakub.contacts.models.asm.ContactAsm;
import czachor.jakub.contacts.models.entities.Contact;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class ContactMapper {
    private ModelMapper modelMapper;
    private ContactAsm contactAsm;

    public ContactMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        configureMappings();
        this.contactAsm = new ContactAsm(this);
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<Contact, ContactDTO>() {
            @Override
            protected void configure() {
                map().setRId(source.getId());
            }
        });
        modelMapper.addMappings(new PropertyMap<ContactDTO, Contact>() {
            @Override
            protected void configure() {
                map().setId(source.getRId());
            }
        });
    }

    public Contact map(ContactDTO contactDTO) {
        return modelMapper.map(contactDTO, Contact.class);
    }

    public ContactDTO map(Contact contact) {
        return modelMapper.map(contact, ContactDTO.class);
    }

    public ContactDTO mapWithLinks(Contact contact) {
        return contactAsm.toResource(contact);
    }

    public List<ContactDTO> mapWithLinks(Iterable<? extends Contact> entities) {
        return contactAsm.toResources(entities);
    }
}
