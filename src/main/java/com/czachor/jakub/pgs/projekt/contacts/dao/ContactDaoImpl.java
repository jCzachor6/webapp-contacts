package com.czachor.jakub.pgs.projekt.contacts.dao;

import com.czachor.jakub.pgs.projekt.contacts.models.entities.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return query.getResultList();
    }

    @Override
    public void deleteContact(Contact contact) {
        sessionFactory
                .getCurrentSession()
                .delete(contact);
    }

    @Override
    public void addContact(Contact contact) {
         sessionFactory
                .getCurrentSession()
                .persist(contact);
    }

    @Override
    public void editContact(Contact contact) {
        sessionFactory
                .getCurrentSession()
                .update(contact);
    }
}
