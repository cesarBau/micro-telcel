package com.technical.test.service;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.springframework.http.HttpStatus.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import com.technical.test.entity.Rol;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @Mock
    private SequenceGeneratorService sequence;

    @InjectMocks
    private RolServImpl rolService;

    private RolDto rol;

    private Rol rolEntity;

    @BeforeEach
    void setUp() {
        rolEntity = new Rol();
        rolEntity.setId(1);
        rolEntity.setNombre("test");

        rol = new RolDto();
        rol.setId(1);
        rol.setNombre("test");
    }

    @Order(1)
    @Test
    void testSaveRol() {
        given(rolRepository.save(rolEntity)).willReturn(rolEntity);
        given(sequence.generateSequence(Rol.SEQUENCE_NAME)).willReturn(1L);

        RolDto result = rolService.save(rol);
        assertNotNull(result);
    }

    @Order(2)
    @Test
    void testDeleteRol() {
        Integer id = 1;
        Optional<Rol> mockFind = Optional.of(rolEntity);

        given(rolRepository.findById(id)).willReturn(mockFind);
        doNothing().when(rolRepository).deleteById(id);

        rolService.deleteById(id);
        verify(rolRepository, times(1)).deleteById(id);
    }

    @Order(3)
    @Test
    void testDeleteRolNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> rolService.deleteById(id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(4)
    @Test
    void testGetRolById() {
        Integer id = 1;
        Optional<Rol> mockFind = Optional.of(rolEntity);

        given(rolRepository.findById(id)).willReturn(mockFind);

        RolDto response = rolService.findById(id);
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Order(5)
    @Test
    void testGetRolByIdNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> rolService.findById(id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(6)
    @Test
    void testGetRolByName() {
        String name = "test";
        List<Rol> data = new ArrayList<>();
        data.add(rolEntity);

        given(rolRepository.findByNombre(name)).willReturn(data);

        List<RolDto> response = rolService.findByNombre(name);
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Order(7)
    @Test
    void testGetRolByNameNotFound() {
        String name = "test";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> rolService.findByNombre(name));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(8)
    @Test
    void testUpdateRol() {
        Integer id = 1;
        Optional<Rol> mockFind = Optional.of(rolEntity);

        given(rolRepository.findById(id)).willReturn(mockFind);
        given(rolRepository.save(rolEntity)).willReturn(rolEntity);

        RolDto response = rolService.update(id, rol);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("test", response.getNombre());
    }

    @Order(9)
    @Test
    void testUpdateRolNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> rolService.update(id, rol));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(10)
    @Test
    void testExistById() {
        Integer id = 1;
        Optional<Rol> mockFind = Optional.of(rolEntity);

        given(rolRepository.findById(id)).willReturn(mockFind);

        Rol response = rolService.existById(id);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("test", response.getNombre());
    }

    @Order(11)
    @Test
    void testExistByIdIsEmpty() {
        Integer id = 1;
        Optional<Rol> mockFind = Optional.empty();

        given(rolRepository.findById(id)).willReturn(mockFind);

        Rol response = rolService.existById(id);
        assertNull(response);
    }

    @Order(12)
    @Test
    void testGetAllRol() {
        List<Rol> data = new ArrayList<>();
        data.add(rolEntity);
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "id");
        PageRequest pageable = PageRequest.of(0, 10, sort);
        Page<Rol> result = new PageImpl<>(data);

        given(rolRepository.findAll(pageable)).willReturn(result);

        List<RolDto> response = rolService.findAll(0, 10, "asc", "id");
        assertNotNull(response);
        assertEquals(1, response.size());
    }

}
