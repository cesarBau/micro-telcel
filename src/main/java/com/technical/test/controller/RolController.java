package com.technical.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technical.test.entity.dto.RolDto;
import com.technical.test.service.RolService;
import com.technical.test.utils.MessageError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Rol", description = "Api para gestion de roles.")
@Log4j2
@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @Operation(summary = "Creacion de rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "", content = @Content) })
    @PostMapping(value = "/", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public RolDto createRol(@Valid @RequestBody RolDto entity) {
        log.info("Consume controller createRol");
        return rolService.save(entity);
    }

    @Operation(summary = "Obtencion de todos los roles")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = "application/json")
    public List<RolDto> getAllRoll(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "30") Integer size, @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "id") String field) {
        log.info("Consume controller getAllRoll");
        log.info("value of page: " + page);
        log.info("value of size: " + size);
        log.info("value of order: " + order);
        return rolService.findAll(page, size, order, field);
    }

    @Operation(summary = "Obtencion de rol por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.ROL_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = "application/json")
    public RolDto getRolById(@PathVariable Integer id) {
        log.info("Consume controller getRolById");
        log.info("value of id: " + id);
        return rolService.findById(id);
    }

    @Operation(summary = "Obtencion de rol por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.ROL_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/name/{name}", produces = "application/json")
    public List<RolDto> getRolByName(@PathVariable String name) {
        log.info("Consume controller getRolByName");
        log.info("value of name: " + name);
        return rolService.findByNombre(name);
    }

    @Operation(summary = "Eliminacion de rol por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.ROL_NOT_FOUND, content = @Content) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteRol(@PathVariable Integer id) {
        log.info("Consume controller deleteRol");
        log.info("value of id: " + id);
        rolService.deleteById(id);
    }

    @Operation(summary = "Actualizacion de rol por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = MessageError.ROL_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = "400", description = "", content = @Content) })
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public RolDto updateRol(@RequestBody RolDto entity, @PathVariable Integer id) {
        log.info("Consume controller updateRol");
        return rolService.update(id, entity);
    }

}
