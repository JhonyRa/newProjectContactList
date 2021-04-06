package com.newprojectcontact.newprojectcontact.repositories;

import com.newprojectcontact.newprojectcontact.model.entity.Contact;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findById(Long contactId);
}
