package com.newprojectcontact.newprojectcontact.services.impl;

import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Contact;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import com.newprojectcontact.newprojectcontact.repositories.ContactRepository;
import com.newprojectcontact.newprojectcontact.repositories.PersonRepository;
import com.newprojectcontact.newprojectcontact.services.ContactService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Contact> listAll() {
        return this.contactRepository.findAll();
    }

    public Optional<Contact> findById(Long contactId) {
        return this.contactRepository.findById(contactId);
    }


    public Contact create(Contact contact) throws Exception {

        if(contact.getId() != null) {
            throw new ServiceException("Nao eh possivel salvar, pois o id esta preenchido");
        }

        if(!this.isValidEmailAddress(contact.getEmail())){
            throw new ServiceException("Nao eh possivel salvar, endereço de Email inválido.");
        }

        if(contact.getEmail() == null && contact.getPhone() == null){
            throw new ServiceException("Nao eh possivel salvar, favor preencher email ou telefone.");
        }

        Optional<Person> currentPerson = this.personRepository.findById(contact.getPerson().getId());
        if(!currentPerson.isPresent()) {
            throw new ServiceException("Nao eh possivel salvar, pessoa nao existe");
        }

        this.contactRepository.save(contact);

        return contact;
    }

    public Contact update(Contact contact, ContactDTO contactDTO) throws Exception {

        if(contact.getId() == null) {
            throw new ServiceException("Nao eh possivel alterar, pois o id nao esta preenchido");
        }

        if(!this.isValidEmailAddress(contact.getEmail())){
            throw new ServiceException("Nao eh possivel salvar, endereço de Email inválido.");
        }

        if(contact.getEmail() == null && contact.getPhone() == null){
            throw new ServiceException("Nao eh possivel salvar, favor preencher email ou telefone.");
        }

        Optional<Contact> currentContact = this.contactRepository.findById(contact.getId());
        if(!currentContact.isPresent()) {
            throw new ServiceException("Nao eh possivel salvar. O contato nao existe");
        }

        Optional<Person> currentPerson = this.personRepository.findById(contact.getPerson().getId());
        if(!currentPerson.isPresent()) {
            throw new ServiceException("Nao eh possivel salvar, pessoa nao existe");
        }

        contact.setId(contactDTO.getId());
        contact.getPerson().setId(contactDTO.getPersonId());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());

        this.contactRepository.save(contact);

        return contact;
    }

    public boolean delete(Long contactId) throws Exception {

        Optional<Contact> currentContact = this.contactRepository.findById(contactId);
        if(!currentContact.isPresent()) {
            throw new ServiceException("Nao eh possivel deletar pois o objeto nao existe");
        }

        this.contactRepository.delete(currentContact.get());

        return true;
    }

    public boolean isValidEmailAddress(String email) throws Exception {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
