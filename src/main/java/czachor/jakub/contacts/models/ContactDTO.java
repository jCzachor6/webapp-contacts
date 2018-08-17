package czachor.jakub.contacts.models;

import czachor.jakub.contacts.models.entities.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
/**
 * @author Jakub Czachor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ContactDTO extends ResourceSupport{
    private Long rId;

    private String name;

    private String surname;

    private String address;

    private String phoneNumber;

    public ContactDTO(Contact contact) {
        this.rId = contact.getId();
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.address = contact.getAddress();
        this.phoneNumber = contact.getPhoneNumber();
    }
}
