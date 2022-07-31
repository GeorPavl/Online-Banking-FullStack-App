package com.dto;

import com._config._helpers._enums.RoleName;
import com.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter @Setter
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    private RoleName name;

    public RoleDTO(Role role) {
        BeanUtils.copyProperties(role, this);
    }
}
