package com.newprojectcontact.newprojectcontact.services;

import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Contact;

import java.util.List;
import java.util.Optional;


public interface ContactService {

    List<Contact> listAll();

    Optional<Contact> findById(Long contactId);

    Contact create(Contact contact) throws Exception;

    Contact update(Contact contact, ContactDTO contactDTO) throws Exception;

    boolean delete(Long contactId) throws Exception;

    boolean isValidEmailAddress(String email) throws Exception;
}
