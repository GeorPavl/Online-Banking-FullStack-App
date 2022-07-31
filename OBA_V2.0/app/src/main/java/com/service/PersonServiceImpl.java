package com.service;

import com.dto.PersonDTO;
import com.entity.Person;
import com.repository.PersonRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserService userService;

    @Override
    public Person dtoToEntity(PersonDTO personDTO) {
        Person person = new Person();
        BeanUtils.copyProperties(personDTO, person);
        return person;
    }

    @Override
    public List<PersonDTO> list() {
        List<PersonDTO> list = new ArrayList<>();
        for (Person person : personRepository.findAll()) {
            list.add(new PersonDTO(person));
        }
        return list;
    }

    @Override
    public PersonDTO get(Long id) throws NotFoundException {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()) {
            throw new NotFoundException("Person with id: " + id + ", did not found!");
        }
        return new PersonDTO(optionalPerson.get());
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        return new PersonDTO(personRepository.save(dtoToEntity(personDTO)));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            personRepository.deleteById(id);
        }
    }
}
