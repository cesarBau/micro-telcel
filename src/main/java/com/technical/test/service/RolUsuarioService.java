package com.technical.test.service;

import java.util.List;

import com.technical.test.entity.RolUsuario;
import com.technical.test.entity.dto.RolDto;

public interface RolUsuarioService {

    RolUsuario save(RolUsuario rolUsuario);

    RolUsuario findByIdUsuario(Integer id);

    List<RolDto> getRols(List<Integer> id);

    RolUsuario updateByIdUsuario(RolUsuario rolUsuario);

    void deleteById(Integer id);

}
