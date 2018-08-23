package exception;

import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;



public class ContactDoesNotExistExceptionTest {
    @Test
    public void constructorTest() {
        String message = "test message";
        ContactDoesNotExistException contactDoesNotExistException
                = new ContactDoesNotExistException(message);
        contactDoesNotExistException.getMessage();
        assertEquals(message, contactDoesNotExistException.getMessage());
    }

    @Test
    public void instanceOfRuntimeException(){
        ContactDoesNotExistException contactDoesNotExistException
                = new ContactDoesNotExistException();
        assertThat(contactDoesNotExistException, instanceOf(RuntimeException.class));
    }
}
