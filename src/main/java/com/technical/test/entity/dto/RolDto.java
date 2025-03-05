package com.technical.test.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolDto {

    Integer id;
    @NotEmpty(message = "nombre del objeto Rol no debe estar vacío")
    @NotNull(message = "nombre del objeto Rol no debe ser nulo")
    String nombre;

}
