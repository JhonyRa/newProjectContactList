package com.newprojectcontact.newprojectcontact.dto.person;

import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreatePersonDTO {

    private String name;

    private String cpf;

    private String nikname;

}
