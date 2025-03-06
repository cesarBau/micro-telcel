package com.technical.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.technical.test.entity.Rol;
import com.technical.test.entity.RolUsuario;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.repository.RolUsuarioRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RolUsuarioServiceTest {

    @InjectMocks
    private RolUsuarioServImpl rolUsuarioServ;

    @Mock
    private RolUsuarioRepository rolUsuarioRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @Mock
    private RolServImpl rolServ;

    private RolUsuario rolUsuario;

    @BeforeEach
    void setUp() {
        List<Integer> rol = new ArrayList<>();
        rol.add(1);
        rolUsuario = new RolUsuario();
        rolUsuario.setId(1);
        rolUsuario.setIdRol(rol);
    }

    @Order(1)
    @Test
    void testFindByIdUsuario() {
        Integer id = 1;
        given(rolUsuarioRepository.findByIdUsuario(id)).willReturn(rolUsuario);

        RolUsuario data = rolUsuarioServ.findByIdUsuario(id);

        assertNotNull(data);
        assertEquals(rolUsuario.getId(), data.getId());
        assertEquals(rolUsuario.getIdRol().size(), data.getIdRol().size());
    }

    @Order(2)
    @Test
    void testSave() {
        given(rolUsuarioRepository.save(rolUsuario)).willReturn(rolUsuario);
        given(sequenceGenerator.generateSequence(RolUsuario.SEQUENCE_NAME)).willReturn(1L);

        RolUsuario data = rolUsuarioServ.save(rolUsuario);

        assertNotNull(data);
        verify(rolUsuarioRepository, times(1)).save(rolUsuario);
    }

    @Order(3)
    @Test
    void testUpdateByIdUsuario() {
        given(rolUsuarioRepository.save(rolUsuario)).willReturn(rolUsuario);

        RolUsuario data = rolUsuarioServ.updateByIdUsuario(rolUsuario);

        assertNotNull(data);
        verify(rolUsuarioRepository, times(1)).save(rolUsuario);

    }

    @Order(4)
    @Test
    void testDeleteById() {
        Integer id = 1;
        doNothing().when(rolUsuarioRepository).deleteById(id);

        rolUsuarioServ.deleteById(id);

        verify(rolUsuarioRepository, times(1)).deleteById(id);
    }

    @Order(5)
    @Test
    void testGetRols() {
        Integer id = 1;
        List<Integer> rols = new ArrayList<>();
        rols.add(id);
        Rol data = new Rol(id, "test");

        given(rolServ.existById(id)).willReturn(data);
        List<RolDto> response = rolUsuarioServ.getRols(rols);

        assertNotNull(response);
        assertEquals(rols.size(), response.size());
    }

    @Order(6)
    @Test
    void testGetRolsIsNull() {
        Integer id = 1;
        List<Integer> rols = new ArrayList<>();
        rols.add(id);

        given(rolServ.existById(id)).willReturn(null);
        List<RolDto> response = rolUsuarioServ.getRols(rols);

        assertNotNull(response);
        assertNotEquals(rols.size(), response.size());
    }

}
