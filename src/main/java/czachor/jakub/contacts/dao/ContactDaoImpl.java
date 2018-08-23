package czachor.jakub.contacts.dao;

import czachor.jakub.contacts.models.entities.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional
@Repository("contactDao")
public class ContactDaoImpl implements ContactDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContactDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Contact findContactById(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Contact.class, id);
    }

    @Override
    public List<Contact> findAllContacts() {
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery("from Contact");
        List<Contact> list = query.getResultList();
        if(list==null){
            return Collections.emptyList();
        }else{
            return list;
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        sessionFactory
                .getCurrentSession()
                .delete(contact);
    }

    @Override
    public Contact addContact(Contact contact) {
         return (Contact) sessionFactory
                .getCurrentSession()
                .merge(contact);
    }

    @Override
    public void editContact(Contact contact) {
        sessionFactory
                .getCurrentSession()
                .merge(contact);
    }
}
