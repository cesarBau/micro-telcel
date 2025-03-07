package com.technical.test.entity.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    @NotNull(message = "id del objeto Usuario debe ser presente")
    Integer id;
    @NotEmpty(message = "nombre del objeto Usuario no debe estar vacío")
    @NotNull(message = "nombre del objeto Usuario no debe ser nulo")
    String nombre;
    @JsonProperty("apellido_paterno")
    @NotEmpty(message = "apellido_paterno del objeto Usuario no debe estar vacío")
    @NotNull(message = "apellido_paterno del objeto Usuario no debe ser nulo")
    String apellidoPaterno;
    @JsonProperty("apellido_materno")
    @NotEmpty(message = "apellido_materno del objeto Usuario no debe estar vacío")
    @NotNull(message = "apellido_materno del objeto Usuario no debe ser nulo")
    String apellidoMaterno;
    @NotNull(message = "el objeto roles debe ser presente")
    @JsonProperty("roles")
    @Valid
    List<RolDto> idRole;

}
