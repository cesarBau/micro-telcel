package com.technical.test.service;

import static org.springframework.http.HttpStatus.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import com.technical.test.entity.RolUsuario;
import com.technical.test.entity.Usuario;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.entity.dto.UsuarioDto;
import com.technical.test.repository.RolUsuarioRepository;
import com.technical.test.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServImpl usuarioServImpl;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @Mock
    private RolUsuarioService rolUsuarioService;

    @Mock
    private RolUsuarioRepository rolUsuarioRepository;

    private UsuarioDto usuario;

    private RolDto rolDto;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioDto();
        usuario.setId(1);
        usuario.setNombre("test");
        usuario.setApellidoPaterno("testAP");
        usuario.setApellidoMaterno("testAM");
        usuario.setIdRole(new ArrayList<RolDto>());
        rolDto = new RolDto();
        rolDto.setId(1);
        rolDto.setNombre("test");
    }

    @Order(1)
    @Test
    void testGetAllUser() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        List<Usuario> data = new ArrayList<>();
        data.add(userEntity);
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), "id");
        PageRequest pageable = PageRequest.of(0, 10, sort);
        Page<Usuario> result = new PageImpl<>(data);
        List<Integer> rols = new ArrayList<>();
        RolUsuario rolUser = new RolUsuario(id, id, rols);

        when(usuarioRepository.findAll(pageable)).thenReturn(result);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        when(rolUsuarioService.getRols(rols)).thenReturn(new ArrayList<RolDto>());

        List<UsuarioDto> response = usuarioServImpl.findAll(0, 10, "asc", "id");

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Order(2)
    @Test
    void testGetUserByID() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        Optional<Usuario> optionUser = Optional.of(userEntity);
        List<Integer> rols = new ArrayList<>();
        RolUsuario rolUser = new RolUsuario(id, id, rols);

        when(usuarioRepository.findById(id)).thenReturn(optionUser);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        when(rolUsuarioService.getRols(rols)).thenReturn(new ArrayList<RolDto>());

        UsuarioDto response = usuarioServImpl.findById(id);

        assertNotNull(response);
        assertEquals(userEntity.getId(), response.getId());
    }

    @Order(3)
    @Test
    void testGetUserByIDNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.findById(id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(4)
    @Test
    void testGetUserByName() {
        String name = "test";
        Usuario userEntity = new Usuario();
        userEntity.setId(1);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(1);
        List<Usuario> users = new ArrayList<>();
        users.add(userEntity);

        List<Integer> rols = new ArrayList<>();
        RolUsuario rolUser = new RolUsuario(1, 1, rols);

        when(usuarioRepository.findByNombre(name)).thenReturn(users);
        when(rolUsuarioService.findByIdUsuario(1)).thenReturn(rolUser);
        when(rolUsuarioService.getRols(rols)).thenReturn(new ArrayList<RolDto>());

        List<UsuarioDto> response = usuarioServImpl.findByNombre(name);

        assertNotNull(response);
        assertEquals(users.size(), response.size());
    }

    @Order(5)
    @Test
    void testGetUserByNameNotFound() {
        String name = "test";

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.findByNombre(name));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(6)
    @Test
    void testDeleteUserByID() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        Optional<Usuario> optionUser = Optional.of(userEntity);
        List<Integer> rols = new ArrayList<>();
        RolUsuario rolUser = new RolUsuario(id, id, rols);

        when(usuarioRepository.findById(id)).thenReturn(optionUser);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        doNothing().when(rolUsuarioService).deleteById(id);
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioServImpl.deleteById(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Order(7)
    @Test
    void testDeleteUserByIDNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.deleteById(id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(8)
    @Test
    void testInsertRolByID() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        Optional<Usuario> optionUser = Optional.of(userEntity);
        List<Integer> rols = new ArrayList<>();
        rols.add(1);
        RolUsuario rolUser = new RolUsuario(id, id, rols);
        List<RolDto> rolsDto = new ArrayList<>();
        rolsDto.add(rolDto);

        when(usuarioRepository.findById(id)).thenReturn(optionUser);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        when(rolUsuarioService.getRols(rols)).thenReturn(rolsDto);
        when(rolUsuarioService.updateByIdUsuario(rolUser)).thenReturn(rolUser);

        usuarioServImpl.insertRolByID(id, id);

        verify(rolUsuarioService, times(1)).updateByIdUsuario(rolUser);
    }

    @Order(9)
    @Test
    void testInsertRolByIDNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.insertRolByID(id, id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(10)
    @Test
    void testDeleteRolByID() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        Optional<Usuario> optionUser = Optional.of(userEntity);
        List<Integer> rols = new ArrayList<>();
        RolUsuario rolUser = new RolUsuario(id, id, rols);
        List<RolDto> rolsDto = new ArrayList<>();
        rolsDto.add(rolDto);

        when(usuarioRepository.findById(id)).thenReturn(optionUser);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        when(rolUsuarioService.updateByIdUsuario(rolUser)).thenReturn(rolUser);

        usuarioServImpl.deleteRolByID(id, id);

        verify(rolUsuarioService, times(1)).updateByIdUsuario(rolUser);
    }

    @Order(11)
    @Test
    void testDeleteRolByIDNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.deleteRolByID(id, id));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(12)
    @Test
    void testUpdateUser() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        List<Usuario> data = new ArrayList<>();
        data.add(userEntity);
        List<Integer> rols = new ArrayList<>();
        rols.add(id);
        RolUsuario rolUser = new RolUsuario(id, id, rols);
        List<RolDto> dataRolsDto = new ArrayList<>();
        dataRolsDto.add(rolDto);
        usuario.setIdRole(dataRolsDto);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(rolUsuarioService.getRols(rols)).thenReturn(dataRolsDto);
        when(rolUsuarioService.findByIdUsuario(id)).thenReturn(rolUser);
        when(rolUsuarioService.updateByIdUsuario(rolUser)).thenReturn(rolUser);
        when(usuarioRepository.save(any())).thenReturn(userEntity);

        UsuarioDto response = usuarioServImpl.update(id, usuario);

        assertNotNull(response);
    }

    @Order(13)
    @Test
    void testUpdateUserNotFound() {
        Integer id = 1;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> usuarioServImpl.update(id, usuario));

        assertEquals(NOT_FOUND, exception.getStatusCode());
    }

    @Order(14)
    @Test
    void testCreateUser() {
        Integer id = 1;
        Usuario userEntity = new Usuario();
        userEntity.setId(id);
        userEntity.setNombre(usuario.getNombre());
        userEntity.setAPaterno(usuario.getApellidoPaterno());
        userEntity.setAMaterno(usuario.getApellidoPaterno());
        userEntity.setIdTrole(id);
        List<Usuario> data = new ArrayList<>();
        data.add(userEntity);
        List<Integer> rols = new ArrayList<>();
        rols.add(id);
        RolUsuario rolUser = new RolUsuario(id, id, rols);
        List<RolDto> dataRolsDto = new ArrayList<>();
        dataRolsDto.add(rolDto);
        usuario.setIdRole(dataRolsDto);

        when(rolUsuarioService.getRols(rols)).thenReturn(dataRolsDto);
        when(sequenceGenerator.generateSequence(Usuario.SEQUENCE_NAME)).thenReturn(1L);
        lenient().when(sequenceGenerator.generateSequence(RolUsuario.SEQUENCE_NAME)).thenReturn(1L);
        when(rolUsuarioService.save(new RolUsuario(null, id, rols))).thenReturn(rolUser);
        when(usuarioRepository.save(any())).thenReturn(userEntity);

        UsuarioDto response = usuarioServImpl.save(usuario);

        assertNotNull(response);
    }

}
