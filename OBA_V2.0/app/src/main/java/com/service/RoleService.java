package com.service;

import com.dto.RoleDTO;
import com.entity.Role;

import java.util.List;

public interface RoleService {

    Role dtoToEntity(RoleDTO roleDTO);

    List<RoleDTO> list();
}
