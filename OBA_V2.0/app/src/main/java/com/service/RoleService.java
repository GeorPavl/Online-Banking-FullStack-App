package com.service;

import com._config._helpers._enums.RoleName;
import com.dto.RoleDTO;
import com.entity.Role;
import javassist.NotFoundException;

import java.util.List;

public interface RoleService {

    Role dtoToEntity(RoleDTO roleDTO);

    List<RoleDTO> list();

    RoleDTO getByName(RoleName name) throws NotFoundException;
}
