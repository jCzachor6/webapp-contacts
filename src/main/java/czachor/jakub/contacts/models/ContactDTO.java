package czachor.jakub.contacts.models;

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
}
