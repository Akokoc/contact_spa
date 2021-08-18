package com.telran.service;

import org.springframework.stereotype.Service;
import com.telran.entity.Contact;
import com.telran.exeption.ContactNotFoundException;
import com.telran.repo.IContractRepo;

import java.util.List;

@Service
public class ContactService {

    private static final String CONTACT_NOT_FOUND = "Contact not exist";
    private final IContractRepo repo;

    public ContactService(IContractRepo repo) {
        this.repo = repo;

        this.repo.save(new Contact("Vasja", "Pupkin", 18));
        this.repo.save(new Contact("Max", "Mustermann", 35));
        this.repo.save(new Contact("John", "Doe", 25));
        this.repo.save(new Contact("Mark", "Schmidt", 27));
        this.repo.save(new Contact("Anna", "Baumann", 26));
    }

    public List<Contact> getAllContacts() {
        return repo.findAll();
    }

    public Contact getById(int contactId) {
        return repo.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(CONTACT_NOT_FOUND));
    }

    public void addContact(String firstName, String lastName, int age) {
        Contact contact = new Contact(firstName, lastName, age);
        repo.save(contact);
    }

    public void editContact(String firstName, String lastName, int age, int contactId) {
        Contact contact = repo.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(CONTACT_NOT_FOUND));

        if (firstName != null)
            contact.setFirstName(firstName);
        if (lastName != null)
            contact.setLastName(lastName);
        if (age > 0)
            contact.setAge(age);

        repo.save(contact);
    }

    public List<Contact> searchByName(String name) {
        return repo.findAllByFirstNameContainsIgnoreCase(name);
    }

    public void deleteById(int contactId) {
        if (repo.existsById(contactId))
            repo.deleteById(contactId);
        else
            throw new ContactNotFoundException(CONTACT_NOT_FOUND);
    }
}
