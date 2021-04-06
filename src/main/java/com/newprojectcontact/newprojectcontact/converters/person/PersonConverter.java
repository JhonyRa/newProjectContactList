package com.newprojectcontact.newprojectcontact.converters.person;

import com.newprojectcontact.newprojectcontact.converters.contact.ContactConverter;
import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import com.newprojectcontact.newprojectcontact.dto.person.PersonDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Contact;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonConverter {

    @Autowired
    private ContactConverter contactConverter;

    public PersonDTO converterEntityToDto(Person entity){
        PersonDTO dto = new PersonDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setNikname(entity.getNikname());
        dto.setCpf(entity.getCpf());
        if(entity.getContacts() != null){
            if(entity.getContacts().size() > 0){
                List<ContactDTO> contactDtoList = new ArrayList<>();
                for(Contact contact : entity.getContacts()){
                    ContactDTO contactToDto = this.contactConverter.converterEntityToDto(contact);
                    contactDtoList.add(contactToDto);
                }
                dto.setContacts(contactDtoList);
            }
        }
        return dto;
    }

    public Person converterDtoToEntity(PersonDTO dto){
        Person entity = new Person();
        entity.setName(dto.getName());
        if(dto.getNikname() != null){
            entity.setNikname(dto.getNikname());
        }
        entity.setCpf(entity.getCpf());
        if(dto.getContacts() !=null){
            if(dto.getContacts().size() > 0){
                List<Contact> contactsList = new ArrayList<>();
                for(ContactDTO contactDTO : dto.getContacts()){
                    Contact dtoToEntity = this.contactConverter.converterDtoToEntity(contactDTO);
                    contactsList.add(dtoToEntity);
                }
                entity.setContacts(contactsList);
            }
        }

        return entity;
    }
}
