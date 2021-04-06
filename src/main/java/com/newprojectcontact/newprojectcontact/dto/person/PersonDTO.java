package com.newprojectcontact.newprojectcontact.dto.person;

import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import lombok.Data;

import java.util.List;

@Data
public class PersonDTO {

    private Long id;

    private String name;

    private String cpf;

    private String nikname;

    private List<ContactDTO> contacts;
}
