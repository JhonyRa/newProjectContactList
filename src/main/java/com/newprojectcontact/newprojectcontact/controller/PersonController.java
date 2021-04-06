package com.newprojectcontact.newprojectcontact.controller;

import com.newprojectcontact.newprojectcontact.converters.person.CreatePersonConverter;
import com.newprojectcontact.newprojectcontact.converters.person.PersonConverter;
import com.newprojectcontact.newprojectcontact.dto.person.CreatePersonDTO;
import com.newprojectcontact.newprojectcontact.dto.person.PersonDTO;
import com.newprojectcontact.newprojectcontact.dto.person.UpdatePersonDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import com.newprojectcontact.newprojectcontact.response.ResponseApi;
import com.newprojectcontact.newprojectcontact.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Person")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private CreatePersonConverter createPersonConverter;

    @GetMapping("/list")
    public ResponseEntity<ResponseApi<List<PersonDTO>>> getPersons() {
        ResponseApi<List<PersonDTO>> response = new ResponseApi<List<PersonDTO>>();
        List<Person> personList = this.personService.listAll();
        try {
            if (!(personList != null && !personList.isEmpty())) {
                List<String> errors = Arrays.asList("Nenhuma pessoa cadastrada.");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.setData(this.transformListEntityToDto(personList));

        } catch (Exception e) {
            e.printStackTrace();
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseApi<PersonDTO>> createContact(@RequestBody CreatePersonDTO requestDto) {
        ResponseApi<PersonDTO> response = new ResponseApi<>();
        try {
            Person entity = this.createPersonConverter.converterDtoToEntity(requestDto);
            Person contactSaved = this.personService.create(entity);

            response.setData(this.personConverter.converterEntityToDto(contactSaved));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseApi<PersonDTO>> updatePerson(@RequestBody UpdatePersonDTO requestDto) {
        ResponseApi<PersonDTO> response = new ResponseApi<>();
        try {
            Optional<Person> entity = this.personService.findById(requestDto.getId());
            if (!entity.isPresent()) {
                List<String> errors = Arrays.asList("Não foi encontrado nenhuma pessoa com esse ID.");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Person entityUpdated = this.personService.update(entity.get(), requestDto);
            PersonDTO personDTO = this.personConverter.converterEntityToDto(entityUpdated);
            response.setData(personDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> deletePerson(@PathVariable(value = "id") Long personId){
        ResponseApi<String> response = new ResponseApi<>();
        try {
            Optional<Person> person = this.personService.findById(personId);

            if (!person.isPresent()) {
                List<String> errors = Arrays.asList("Não foi encontrado nenhuma pessoa para esse ID.");
                response.setErrors(errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            this.personService.delete(personId);
            response.setData("Contato com o id "+person.get().getId().toString()+" foi excluído com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            List<String> erros = Arrays.asList(e.getMessage());
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    private List<PersonDTO> transformListEntityToDto(List<Person> persons) {
        return persons.stream().map(rest -> this.personConverter.converterEntityToDto(rest))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
