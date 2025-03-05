package com.technical.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.technical.test.entity.Rol;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.repository.RolRepository;
import com.technical.test.utils.MessageError;
import com.technical.test.utils.RolMapp;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RolServImpl implements RolService {

    private RolRepository rolRepository;
    private SequenceGeneratorService sequenceGeneratorService;

    public RolServImpl(RolRepository rolRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.rolRepository = rolRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Consume service deleteById");
        Optional<Rol> data = rolRepository.findById(id);
        if (data.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null);
        } else {
            rolRepository.deleteById(id);

        }
    }

    @Override
    public List<RolDto> findAll(Integer page, Integer size, String order, String field) {
        log.info("Consume service findAll");
        Sort sort = Sort.by(Sort.Direction.fromString(order), field);
        PageRequest pageable = PageRequest.of(page, size, sort);
        List<Rol> data = rolRepository.findAll(pageable).getContent();
        return RolMapp.toRolDtoList(data);
    }

    @Override
    public RolDto findById(Integer id) {
        log.info("Consume service findById");
        Optional<Rol> data = rolRepository.findById(id);
        if (data.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null);
        } else {
            return RolMapp.toRolDto(data.get());
        }
    }

    @Override
    public List<RolDto> findByNombre(String nombre) {
        log.info("Consume service findByNombre");
        List<Rol> data = rolRepository.findByNombre(nombre);
        if (!data.isEmpty()) {
            return RolMapp.toRolDtoList(data);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null);
        }
    }

    @Override
    public RolDto save(RolDto rol) {
        log.info("Consume service save");
        Rol conversion = RolMapp.toRol(rol);
        conversion.setId((int) sequenceGeneratorService.generateSequence(Rol.SEQUENCE_NAME));
        Rol data = rolRepository.save(conversion);
        return RolMapp.toRolDto(data);
    }

    @Override
    public RolDto update(Integer id, RolDto rol) {
        log.info("Consume service update");
        Optional<Rol> data = rolRepository.findById(id);
        if (data.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null);
        } else {
            Rol conversion = RolMapp.toRol(rol);
            conversion.setId(id);
            Rol result = rolRepository.save(conversion);
            return RolMapp.toRolDto(result);
        }
    }

    @Override
    public Rol existById(Integer id) {
        log.info("Consume service existById");
        Optional<Rol> data = rolRepository.findById(id);
        if (data.isEmpty()) {
            return null;
        } else {
            return data.get();
        }
    }

}
