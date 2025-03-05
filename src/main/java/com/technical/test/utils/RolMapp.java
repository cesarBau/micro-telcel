package com.technical.test.utils;

import java.util.List;

import com.technical.test.entity.Rol;
import com.technical.test.entity.dto.RolDto;

public class RolMapp {

    public static RolDto toRolDto(Rol rol) {
        return new RolDto(rol.getId(), rol.getNombre());
    }

    public static Rol toRol(RolDto rolDto) {
        return new Rol(rolDto.getId(), rolDto.getNombre());
    }

    public static List<RolDto> toRolDtoList(List<Rol> roles) {
        return roles.stream().map(RolMapp::toRolDto).toList();
    }

    public static List<Rol> toRolList(List<RolDto> rolesDto) {
        return rolesDto.stream().map(RolMapp::toRol).toList();
    }

}
