package com.technical.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.technical.test.entity.Rol;
import com.technical.test.entity.RolUsuario;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.repository.RolUsuarioRepository;
import com.technical.test.utils.RolMapp;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RolUsuarioServImpl implements RolUsuarioService {

    private RolService rolService;
    private RolUsuarioRepository rolUsuarioRepository;
    private SequenceGeneratorService sequenceGeneratorService;

    public RolUsuarioServImpl(RolService rolService, RolUsuarioRepository rolUsuarioRepository,
            SequenceGeneratorService sequenceGeneratorService) {
        this.rolService = rolService;
        this.rolUsuarioRepository = rolUsuarioRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public RolUsuario findByIdUsuario(Integer id) {
        log.info("Consume service findByIdUsuario");
        return rolUsuarioRepository.findByIdUsuario(id);
    }

    @Override
    public RolUsuario save(RolUsuario rolUsuario) {
        log.info("Consume service save");
        rolUsuario.setId((int) sequenceGeneratorService.generateSequence(RolUsuario.SEQUENCE_NAME));
        return rolUsuarioRepository.save(rolUsuario);
    }

    @Override
    public List<RolDto> getRols(List<Integer> id) {
        log.info("Consume service getRol");
        List<Rol> existRol = new ArrayList<>();
        for (Integer search : id) {
            Rol macth = rolService.existById(search);
            if (macth != null) {
                existRol.add(macth);
            }
        }
        return RolMapp.toRolDtoList(existRol);
    }

    @Override
    public RolUsuario updateByIdUsuario(RolUsuario rolUsuario) {
        log.info("Consume service updateByIdUsuario");
        return rolUsuarioRepository.save(rolUsuario);
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Consume service updateByIdUsuario");
        rolUsuarioRepository.deleteById(id);
    }

    

}
