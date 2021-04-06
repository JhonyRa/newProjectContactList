package com.newprojectcontact.newprojectcontact.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "PERSON")
public class Person {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "NICKNAME", nullable = true)
    private String nikname;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Contact> contacts;
}
