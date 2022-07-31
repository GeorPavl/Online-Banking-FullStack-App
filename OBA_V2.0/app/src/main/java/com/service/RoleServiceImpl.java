package com.service;

import com.dto.RoleDTO;
import com.entity.Role;
import com.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role dtoToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return role;
    }

    @Override
    public List<RoleDTO> list() {
        List<RoleDTO> list = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            list.add(new RoleDTO(role));
        }
        return list;
    }
}
