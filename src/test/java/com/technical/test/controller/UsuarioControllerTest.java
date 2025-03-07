package com.technical.test.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.test.entity.dto.RolDto;
import com.technical.test.entity.dto.UsuarioDto;
import com.technical.test.service.UsuarioService;
import com.technical.test.utils.MessageError;

@WebMvcTest(UsuarioController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerTest {

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDto usuarioDto;

    @BeforeEach
    void setUp() {
        List<RolDto> rol = new ArrayList<>();
        rol.add(new RolDto(1, "test"));
        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1);
        usuarioDto.setNombre("test");
        usuarioDto.setApellidoPaterno("testAP");
        usuarioDto.setApellidoMaterno("testAM");
        usuarioDto.setIdRole(rol);
    }

    @Order(1)
    @Test
    void testGetAllUser() {
        ResultActions result;
        List<UsuarioDto> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.add(usuarioDto);
        }
        when(usuarioService.findAll(0, 10, "asc", "id")).thenReturn(response);
        try {
            result = mockMvc.perform(get("/usuario"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(2)
    @Test
    void testGetUserById() {
        ResultActions result;
        Integer id = 1;
        when(usuarioService.findById(id)).thenReturn(usuarioDto);
        try {
            result = mockMvc.perform(get("/usuario/{id}", id));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(3)
    @Test
    void testGetUserByName() {
        ResultActions result;
        List<UsuarioDto> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.add(usuarioDto);
        }
        String name = "test";
        when(usuarioService.findByNombre(name)).thenReturn(response);
        try {
            result = mockMvc.perform(get("/usuario/nombre/{name}", name));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(4)
    @Test
    void testGetUserByIdNotFound() {
        ResultActions result;
        Integer id = 1;
        when(usuarioService.findById(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null));
        try {
            result = mockMvc.perform(get("/usuario/{id}", id));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(5)
    @Test
    void testGetUserByNameNotFound() {
        ResultActions result;
        List<UsuarioDto> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.add(usuarioDto);
        }
        String name = "test";
        when(usuarioService.findByNombre(name))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null));
        try {
            result = mockMvc.perform(get("/usuario/nombre/{name}", name));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(6)
    @Test
    void testCreateUser() {
        ResultActions result;
        when(usuarioService.save(usuarioDto)).thenReturn(usuarioDto);
        try {
            result = mockMvc.perform(post("/usuario")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(usuarioDto)));
            result.andExpect(status().isCreated());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(7)
    @Test
    void testUpdateUser() {
        ResultActions result;
        Integer id = 1;
        when(usuarioService.update(id, usuarioDto)).thenReturn(usuarioDto);
        try {
            result = mockMvc.perform(put("/usuario/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(usuarioDto)));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(8)
    @Test
    void testUpdateUserNotFound() {
        ResultActions result;
        Integer id = 1;
        when(usuarioService.update(id, usuarioDto))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null));
        try {
            result = mockMvc.perform(put("/usuario/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(usuarioDto)));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(9)
    @Test
    void testInsertRol() {
        ResultActions result;
        Integer idUser = 1;
        Integer idRol = 1;
        doNothing().when(usuarioService).insertRolByID(idUser, idRol);
        try {
            result = mockMvc.perform(patch("/usuario/1/rol/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(usuarioDto)));
            result.andExpect(status().isNoContent());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(10)
    @Test
    void testInsertRolNotFound() {
        ResultActions result;
        Integer idUser = 1;
        Integer idRol = 1;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null))
                .when(usuarioService).insertRolByID(idUser, idRol);
        try {
            result = mockMvc.perform(patch("/usuario/1/rol/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(usuarioDto)));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(11)
    @Test
    void testDeleteUser() {
        ResultActions result;
        Integer id = 1;
        doNothing().when(usuarioService).deleteById(id);
        try {
            result = mockMvc.perform(delete("/usuario/{id}", id));
            result.andExpect(status().isNoContent());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(12)
    @Test
    void testDeleteUserNotFound() {
        ResultActions result;
        Integer id = 1;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null))
                .when(usuarioService).deleteById(id);
        try {
            result = mockMvc.perform(delete("/usuario/{id}", id));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(13)
    @Test
    void testDeleteRolUser() {
        ResultActions result;
        Integer idUser = 1;
        Integer idRol = 1;
        doNothing().when(usuarioService).deleteRolByID(idUser, idRol);
        try {
            result = mockMvc.perform(delete("/usuario/1/rol/1"));
            result.andExpect(status().isNoContent());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(14)
    @Test
    void testDeleteRolUserNotFound() {
        ResultActions result;
        Integer idUser = 1;
        Integer idRol = 1;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.USER_NOT_FOUND, null))
                .when(usuarioService).deleteRolByID(idUser, idRol);
        try {
            result = mockMvc.perform(delete("/usuario/1/rol/1"));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
