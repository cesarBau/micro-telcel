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
import com.technical.test.service.RolService;
import com.technical.test.utils.MessageError;

@WebMvcTest(RolController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private RolDto rol;

    @BeforeEach
    void setUp() {
        rol = new RolDto();
        rol.setId(1);
        rol.setNombre("test");
    }

    @Order(1)
    @Test
    void testGetAllRol() {
        ResultActions result;
        List<RolDto> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.add(rol);
        }
        when(rolService.findAll(0, 10, "asc", "id")).thenReturn(response);
        try {
            result = mockMvc.perform(get("/rol/"));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(2)
    @Test
    void testGetRolById() {
        ResultActions result;
        Integer id = 1;
        when(rolService.findById(id)).thenReturn(rol);
        try {
            result = mockMvc.perform(get("/rol/{id}", id));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(3)
    @Test
    void testGetRolByName() {
        ResultActions result;
        String name = "test";
        List<RolDto> response = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            response.add(rol);
        }
        when(rolService.findByNombre(name)).thenReturn(response);
        try {
            result = mockMvc.perform(get("/rol/name/{name}", name));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(4)
    @Test
    void testGetRolByIdNotFound() {
        ResultActions result;
        Integer id = 0;
        when(rolService.findById(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null));
        try {
            result = mockMvc.perform(get("/rol/{id}"));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(5)
    @Test
    void testGetRolByNameNotFound() {
        ResultActions result;
        String name = "test";
        when(rolService.findByNombre(name))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null));
        try {
            result = mockMvc.perform(get("/rol/name/{name}", name));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(6)
    @Test
    void testCreateRol() {
        ResultActions result;
        when(rolService.save(rol)).thenReturn(rol);
        try {
            result = mockMvc.perform(post("/rol/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(rol)));
            result.andExpect(status().isCreated());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(7)
    @Test
    void testDeleteRol() {
        ResultActions result;
        Integer id = 1;
        doNothing().when(rolService).deleteById(id);
        try {
            result = mockMvc.perform(delete("/rol/{id}", id));
            result.andExpect(status().isNoContent());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(8)
    @Test
    void testUpdateRol() {
        ResultActions result;
        Integer id = 1;
        when(rolService.update(id, rol)).thenReturn(rol);
        try {
            result = mockMvc.perform(put("/rol/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(rol)));
            result.andExpect(status().isOk());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(9)
    @Test
    void testDeleteRolNotFound() {
        ResultActions result;
        Integer id = 1;
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null)).when(rolService)
                .deleteById(id);
        try {
            result = mockMvc.perform(delete("/rol/{id}", id));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Order(10)
    @Test
    void testUpdateRolNotFound() {
        ResultActions result;
        Integer id = 1;
        when(rolService.update(id, rol))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageError.ROL_NOT_FOUND, null));
        try {
            result = mockMvc.perform(put("/rol/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(rol)));
            result.andExpect(status().isNotFound());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
