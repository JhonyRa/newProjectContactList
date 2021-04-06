package com.newprojectcontact.newprojectcontact.converters.person;

import com.newprojectcontact.newprojectcontact.dto.person.CreatePersonDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class CreatePersonConverter {

    public Person converterDtoToEntity(CreatePersonDTO dto){
        Person entity = new Person();
        entity.setName(dto.getName());
        if(dto.getNikname() != null){
            entity.setNikname(dto.getNikname());
        }
        entity.setCpf(dto.getCpf());

        return entity;
    }
}
