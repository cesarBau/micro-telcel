package com.technical.test.service;

import java.util.List;

import com.technical.test.entity.Rol;
import com.technical.test.entity.dto.RolDto;

public interface RolService {

    RolDto save(RolDto rol);

    List<RolDto> findAll(Integer page, Integer size, String order, String field);

    RolDto findById(Integer id);

    List<RolDto> findByNombre(String nombre);

    void deleteById(Integer id);

    RolDto update(Integer id, RolDto rol);

    Rol existById(Integer id);

}
