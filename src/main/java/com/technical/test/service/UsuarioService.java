package com.technical.test.service;

import java.util.List;

import com.technical.test.entity.dto.UsuarioDto;

public interface UsuarioService {

    UsuarioDto save(UsuarioDto usuario);

    List<UsuarioDto> findAll(Integer page, Integer size, String order, String field);

    UsuarioDto findById(Integer id);

    List<UsuarioDto> findByNombre(String nombre);

    void deleteById(Integer id);

    UsuarioDto update(Integer id, UsuarioDto usuario);

    void deleteRolByID(Integer idUsuario, Integer idRol);

    void insertRolByID(Integer idUsuario, Integer idRol);
}
