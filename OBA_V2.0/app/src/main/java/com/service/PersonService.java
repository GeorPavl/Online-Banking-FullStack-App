package com.service;

import com.dto.PersonDTO;
import com.entity.Person;
import javassist.NotFoundException;

import java.util.List;

public interface PersonService {

    Person dtoToEntity(PersonDTO personDTO);

    List<PersonDTO> list();

    PersonDTO get(Long id) throws NotFoundException;

    PersonDTO save(PersonDTO personDTO);

    void delete(Long id) throws NotFoundException;
}
