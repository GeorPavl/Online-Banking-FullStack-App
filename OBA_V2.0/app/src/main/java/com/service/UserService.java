package com.service;

import com.dto.UserDTO;
import com.entity.User;
import javassist.NotFoundException;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    User dtoToEntity(UserDTO userDTO) throws NotFoundException;

    List<UserDTO> list();

    UserDTO get(Long id) throws NotFoundException;

    UserDTO getByUsername(String username) throws NotFoundException;

    UserDTO getByTokenAndCode(String token, String code) throws NotFoundException;

    UserDTO save(UserDTO userDTO) throws NotFoundException;

    UserDTO register(UserDTO userDTO) throws NotFoundException, MessagingException;

    void verify(String token, String code) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
