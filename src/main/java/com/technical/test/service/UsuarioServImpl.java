package com.technical.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.technical.test.entity.RolUsuario;
import com.technical.test.entity.Usuario;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.entity.dto.UsuarioDto;
import com.technical.test.repository.UsuarioRepository;
import com.technical.test.utils.MessageError;
import com.technical.test.utils.UsuarioMapp;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UsuarioServImpl implements UsuarioService {

    private RolUsuarioService rolUsuarioService;
    private UsuarioRepository usuarioRepository;
    private SequenceGeneratorService sequenceGeneratorService;

    public UsuarioServImpl(RolUsuarioService rolUsuarioService, UsuarioRepository usuarioRepository,
            SequenceGeneratorService sequenceGeneratorService) {
        this.rolUsuarioService = rolUsuarioService;
        this.usuarioRepository = usuarioRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Consume service deleteById");
        Optional<Usuario> exist = usuarioRepository.findById(id);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        // Buscamos y eliminamos la relacion con el Rol.
        RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(id);
        rolUsuarioService.deleteById(getRolUser.getId());
        // Eliminamos el usuario
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<UsuarioDto> findAll(Integer page, Integer size, String order, String field) {
        log.info("Consume service findAll");
        List<UsuarioDto> response = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.fromString(order), field);
        PageRequest pageable = PageRequest.of(page, size, sort);
        List<Usuario> search = usuarioRepository.findAll(pageable).getContent();
        for (Usuario user : search) {
            // Buscamos la relacion con el Rol.
            RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(user.getId());
            List<RolDto> rols = rolUsuarioService.getRols(getRolUser.getIdRol());
            // Generamos el dto
            UsuarioDto usuarioDto = UsuarioMapp.toUsuarioDto(user);
            usuarioDto.setIdRole(rols);
            response.add(usuarioDto);
        }
        return response;
    }

    @Override
    public UsuarioDto findById(Integer id) {
        log.info("Consume service findById");
        Optional<Usuario> exist = usuarioRepository.findById(id);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        // Buscamos la relacion con el Rol.
        RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(id);
        List<RolDto> rols = rolUsuarioService.getRols(getRolUser.getIdRol());
        // Generamos el response
        UsuarioDto response = UsuarioMapp.toUsuarioDto(exist.get());
        response.setIdRole(rols);
        return response;
    }

    @Override
    public List<UsuarioDto> findByNombre(String nombre) {
        log.info("Consume service findByNombre");
        List<UsuarioDto> response = new ArrayList<>();
        List<Usuario> exist = usuarioRepository.findByNombre(nombre);
        if (exist.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        for (Usuario usuario : exist) {
            // Buscamos la relacion con el Rol.
            RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(usuario.getId());
            List<RolDto> rols = rolUsuarioService.getRols(getRolUser.getIdRol());
            // Generamos el dto
            UsuarioDto usuarioDto = UsuarioMapp.toUsuarioDto(usuario);
            usuarioDto.setIdRole(rols);
            response.add(usuarioDto);
        }
        return response;
    }

    @Override
    public UsuarioDto save(UsuarioDto usuario) {
        log.info("Consume service save");
        List<Integer> idRol = new ArrayList<>();
        // Extraemos los identificadores de los roles
        for (RolDto rol : usuario.getIdRole()) {
            idRol.add(rol.getId());
        }
        // Validamos si existen los roles, solo se agregan los existentes.
        List<RolDto> validsRol = rolUsuarioService.getRols(idRol);
        // Casteamos y generamos el identificador para RolUsuario
        Usuario convertion = UsuarioMapp.toUsuario(usuario);
        convertion.setId((int) sequenceGeneratorService.generateSequence(Usuario.SEQUENCE_NAME));
        // Extraemos los identificadores de los roles existentes y actualizamos
        idRol.clear();
        for (RolDto rol : validsRol) {
            idRol.add(rol.getId());
        }
        // Creamos la relacion con el rol
        RolUsuario rolUsuario = new RolUsuario();
        rolUsuario.setIdUsuario(convertion.getId());
        rolUsuario.setIdRol(idRol);
        RolUsuario rolUsuarioResponse = rolUsuarioService.save(rolUsuario);
        // Creamos el usuario
        convertion.setIdTrole(rolUsuarioResponse.getId());
        Usuario data = usuarioRepository.save(convertion);
        // Generamos el response
        UsuarioDto response = UsuarioMapp.toUsuarioDto(data);
        response.setIdRole(validsRol);
        return response;
    }

    @Override
    public UsuarioDto update(Integer id, UsuarioDto usuario) {
        log.info("Consume service update");
        List<Integer> idRol = new ArrayList<>();
        // Buscamos si exite el usuario
        Optional<Usuario> exist = usuarioRepository.findById(id);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        // Extraemos los identificadores de los nuevos roles
        for (RolDto rol : usuario.getIdRole()) {
            idRol.add(rol.getId());
        }
        // Validamos si existen los roles, solo se agregan los existentes.
        List<RolDto> validsRol = rolUsuarioService.getRols(idRol);
        // Casteamos los nuevos valores del usuario
        Usuario convertion = UsuarioMapp.toUsuario(usuario);
        convertion.setId(exist.get().getId());
        // Buscamos la relacion con el rol
        RolUsuario rolUser = rolUsuarioService.findByIdUsuario(id);
        // Extraemos los identificadores de los roles existentes y actualizamos
        idRol.clear();
        for (RolDto rol : validsRol) {
            idRol.add(rol.getId());
        }
        rolUser.setIdRol(idRol);
        RolUsuario rolUserResponse = rolUsuarioService.updateByIdUsuario(rolUser);
        // Actualizamos el usuario
        convertion.setIdTrole(rolUserResponse.getId());
        Usuario data = usuarioRepository.save(convertion);
        // Generamos el response
        UsuarioDto response = UsuarioMapp.toUsuarioDto(data);
        response.setIdRole(validsRol);
        return response;
    }

    @Override
    public void deleteRolByID(Integer idUsuario, Integer idRol) {
        log.info("Consume service deleteById");
        Optional<Usuario> exist = usuarioRepository.findById(idUsuario);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        // Buscamos y eliminamos de la relacion el rol.
        RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(idUsuario);
        List<Integer> rols = getRolUser.getIdRol();
        rols.remove(new Integer(idRol));
        getRolUser.setIdRol(rols);
        rolUsuarioService.updateByIdUsuario(getRolUser);
    }

    @Override
    public void insertRolByID(Integer idUsuario, Integer idRol) {
        log.info("Consume service insertRolByID");
        Optional<Usuario> exist = usuarioRepository.findById(idUsuario);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null);
        }
        // Buscamos y agregamos el rol.
        RolUsuario getRolUser = rolUsuarioService.findByIdUsuario(idUsuario);
        List<Integer> rols = getRolUser.getIdRol();
        rols.add(idRol);
        // Validamos si existen los roles, solo se agregan los existentes.
        List<RolDto> validsRol = rolUsuarioService.getRols(rols);
        // Extraemos los identificadores de los roles existentes y actualizamos
        rols.clear();
        for (RolDto rol : validsRol) {
            rols.add(rol.getId());
        }
        getRolUser.setIdRol(rols);
        rolUsuarioService.updateByIdUsuario(getRolUser);
    }

}
