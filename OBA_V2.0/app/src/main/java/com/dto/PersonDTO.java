package com.dto;

import com.entity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    public PersonDTO(Person person) {
        BeanUtils.copyProperties(person, this);
    }
}
