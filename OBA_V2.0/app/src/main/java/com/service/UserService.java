package com.service;

import com.dto.UserDTO;
import com.entity.User;
import javassist.NotFoundException;

import java.util.List;

public interface UserService {

    User dtoToEntity(UserDTO userDTO) throws NotFoundException;

    List<UserDTO> list();

    UserDTO get(Long id) throws NotFoundException;

    UserDTO save(UserDTO userDTO) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
