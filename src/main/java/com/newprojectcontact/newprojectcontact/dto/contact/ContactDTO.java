package com.newprojectcontact.newprojectcontact.dto.contact;

import lombok.Data;

@Data
public class ContactDTO {

    private Long id;

    private String phone;

    private String email;

    private Long personId;
}
