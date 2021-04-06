package com.newprojectcontact.newprojectcontact.controller;

import com.newprojectcontact.newprojectcontact.converters.contact.ContactConverter;
import com.newprojectcontact.newprojectcontact.dto.contact.ContactDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Contact;
import com.newprojectcontact.newprojectcontact.response.ResponseApi;
import com.newprojectcontact.newprojectcontact.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactConverter contactConverter;

    @GetMapping("/list")
    public ResponseEntity<ResponseApi<List<ContactDTO>>> getContacts() {
        ResponseApi<List<ContactDTO>> response = new ResponseApi<List<ContactDTO>>();
        List<Contact> contacts = this.contactService.listAll();
        try {
            if (!(contacts != null && !contacts.isEmpty())) {
                List<String> errors = Arrays.asList("Nenhum contato cadastrado. ");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.setData(this.transformListEntityToDto(contacts));

        } catch (Exception e) {
            e.printStackTrace();
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseApi<ContactDTO>> createContact(@RequestBody ContactDTO requestDto) {
        ResponseApi<ContactDTO> response = new ResponseApi<>();
        try {
            Contact entity = this.contactConverter.converterDtoToEntity(requestDto);
            Contact contactSaved = this.contactService.create(entity);

            response.setData(this.contactConverter.converterEntityToDto(contactSaved));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseApi<ContactDTO>> updateContact(@RequestBody ContactDTO requestDto) {
        ResponseApi<ContactDTO> response = new ResponseApi<>();
        try {
            Optional<Contact> entity = this.contactService.findById(requestDto.getId());
            if (!entity.isPresent()) {
                List<String> errors = Arrays.asList("Não foi encontrado contato com esse ID.");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Contact entityUpdated = this.contactService.update(entity.get(), requestDto);
            ContactDTO contactDTO = this.contactConverter.converterEntityToDto(entityUpdated);
            response.setData(contactDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> deleteContact(@PathVariable(value = "id") Long contactId){
        ResponseApi<String> response = new ResponseApi<>();
        try {
            Optional<Contact> digitalMagazine = this.contactService.findById(contactId);

            if (!digitalMagazine.isPresent()) {
                List<String> errors = Arrays.asList("Não foi encontrado nenhum contato para esse ID.");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            this.contactService.delete(contactId);
            response.setData("Pessoa com o id "+digitalMagazine.get().getId().toString()+" foi excluído com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    private List<ContactDTO> transformListEntityToDto(List<Contact> contacts) {
        return contacts.stream().map(rest -> this.contactConverter.converterEntityToDto(rest))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}