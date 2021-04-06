package com.newprojectcontact.newprojectcontact.services;

import com.newprojectcontact.newprojectcontact.dto.person.UpdatePersonDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> listAll();

    Optional<Person> findById(Long personId);

    Person create(Person person) throws Exception;

    Person update(Person person, UpdatePersonDTO updatePersonDTO) throws Exception;

    boolean delete(Long pesonId) throws Exception;

    boolean cpfValidate(String cpf);
}
