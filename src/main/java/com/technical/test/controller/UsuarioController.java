package com.technical.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.technical.test.entity.dto.UsuarioDto;
import com.technical.test.service.UsuarioService;
import com.technical.test.utils.MessageError;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Usuario", description = "Api para gestion de usuarios.")
@Log4j2
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Creacion de usuario", description = "Solo se agregaran roles exitentes en la relacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "", content = @Content) })
    @PostMapping(value = "", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto createUser(@Valid @RequestBody UsuarioDto entity) {
        log.info("Consume controller createUser");
        return usuarioService.save(entity);
    }

    @Operation(summary = "Obtencion de todos los usuarios")
    @GetMapping(value = "", produces = "application/json")
    public List<UsuarioDto> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "30") Integer size, @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "id") String field) {
        log.info("Consume controller getAllUsers");
        return usuarioService.findAll(page, size, order, field);
    }

    @Operation(summary = "Obtencion de usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    public UsuarioDto getUserById(@PathVariable Integer id) {
        log.info("Consume controller getUserById");
        return usuarioService.findById(id);
    }

    @Operation(summary = "Obtencion de usuario por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/nombre/{nombre}", produces = "application/json")
    public List<UsuarioDto> getUserByName(@PathVariable String nombre) {
        log.info("Consume controller getUserByName");
        return usuarioService.findByNombre(nombre);
    }

    @Operation(summary = "Actualizacion de usuario por id", description = "Solo se agregaran roles exitentes en la relacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public UsuarioDto updateUser(@PathVariable Integer id, @Valid @RequestBody UsuarioDto entity) {
        log.info("Consume controller updateUser");
        return usuarioService.update(id, entity);
    }

    @Operation(summary = "Eliminacion de usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteUser(@PathVariable Integer id) {
        log.info("Consume controller deleteUser");
        usuarioService.deleteById(id);
    }

    @Operation(summary = "Eliminacion de rol por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{idUsuario}/rol/{idRol}", produces = "application/json")
    public void deleteRolUser(@PathVariable Integer idUsuario, @PathVariable Integer idRol) {
        log.info("Consume controller deleteRolUser");
        usuarioService.deleteRolByID(idUsuario, idRol);
    }

    @Operation(summary = "Insertar rol a usuario por id", description = "Solo se agregaran roles exitentes en la relacion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.USER_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{idUsuario}/rol/{idRol}", produces = "application/json")
    public void insertRoltoUser(@PathVariable Integer idUsuario, @PathVariable Integer idRol) {
        log.info("Consume controller insertRoltoUser");
        usuarioService.insertRolByID(idUsuario, idRol);
    }

}
