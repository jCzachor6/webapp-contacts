package czachor.jakub.contacts.service.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ContactDoesNotExistException extends RuntimeException{
    public ContactDoesNotExistException(String message) {
        super(message);
    }
}
