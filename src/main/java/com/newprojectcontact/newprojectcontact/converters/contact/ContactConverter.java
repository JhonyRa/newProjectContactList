package com.newprojectcontact.newprojectcontact.converters.contact;

import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Contact;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {

    public ContactDTO converterEntityToDto(Contact entity){
        ContactDTO dto = new ContactDTO();
        dto.setId(entity.getId());
        dto.setPersonId(entity.getPerson().getId());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());

        return dto;
    }

    public Contact converterDtoToEntity(ContactDTO requestDto){
        Contact entity = new Contact();
        Person person = new Person();
        entity.setPerson(person);
        entity.getPerson().setId(requestDto.getPersonId());
        entity.setEmail(requestDto.getEmail());
        entity.setPhone(requestDto.getPhone());

        return entity;
    }
}
