package com.newprojectcontact.newprojectcontact.services.impl;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import com.newprojectcontact.newprojectcontact.converters.person.PersonConverter;
import com.newprojectcontact.newprojectcontact.dto.person.UpdatePersonDTO;
import com.newprojectcontact.newprojectcontact.model.entity.Person;
import com.newprojectcontact.newprojectcontact.repositories.PersonRepository;
import com.newprojectcontact.newprojectcontact.services.PersonService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonConverter personConverter;

    public List<Person> listAll() {
        return this.personRepository.findAll();
    }

    public Optional<Person> findById(Long personId) {
        return this.personRepository.findById(personId);
    }

    public Person create(Person person) throws Exception {

        if(person.getId() != null) {
            throw new ServiceException("Nao eh possivel salvar, pois o id esta preenchido");
        }

        if(person.getName() == null || person.getName().length() < 3) {
            throw new ServiceException("Nao eh possivel salvar, nome está vazio ou não é válido.");
        }

        if(person.getCpf() == null) {
            throw new ServiceException("Nao eh possivel salvar, preencha o CPF.");
        }else{
            if (!cpfValidate(person.getCpf())) {
                throw new ServiceException("Nao eh possivel salvar, CPF inválido.");
            }
        }

        Optional<Person> currentPerson = this.personRepository.findByCpf(person.getCpf());
        if(currentPerson.isPresent()) {
            throw new ServiceException("Nao eh possivel salvar, ja existe uma pessoa cadastrada com esse cpf.");
        }

        this.personRepository.save(person);

        return person;
    }

    public Person update(Person person, UpdatePersonDTO updatePersonDTO) throws Exception {

        if(person.getId() == null) {
            throw new ServiceException("Nao eh possivel alterar, especifique o id da pessoa");
        }

        if(person.getCpf() == null) {
            throw new ServiceException("Nao eh possivel salvar, preencha o CPF.");
        }else{
            if (!cpfValidate(person.getCpf())) {
                throw new ServiceException("Nao eh possivel salvar, CPF inválido.");
            }
        }

        Optional<Person> currentPerson = this.personRepository.findById(person.getId());
        if(!currentPerson.isPresent()) {
            throw new ServiceException("Nao eh possivel alterar, pois a pessoa nao existe");
        }

        person.setId(updatePersonDTO.getId());
        person.setName(updatePersonDTO.getName());
        person.setNikname(updatePersonDTO.getNikname());
        person.setCpf(updatePersonDTO.getCpf());

        this.personRepository.save(person);

        return person;
    }

    public boolean delete(Long pesonId) throws Exception {

        Optional<Person> currentGame = this.personRepository.findById(pesonId);
        if(!currentGame.isPresent()) {
            throw new ServiceException("Nao eh possivel deletar. Pessoa nao existe");
        }

        this.personRepository.delete(currentGame.get());

        return true;
    }

    public boolean cpfValidate(String cpf) throws ServiceException {
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> errors = cpfValidator.invalidMessagesFor(cpf);

        if(errors.size() > 0){
            System.out.println("CPF vazio ou inválido. Verifique e tente novamente.");
            return false;
        }else {
            System.out.println("CPF ok.");
            return true;
        }
    }
}
