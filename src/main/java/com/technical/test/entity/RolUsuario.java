package com.technical.test.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ROLES_USUARIO")
public class RolUsuario {

    @Transient
    public static final String SEQUENCE_NAME = "rols_user_sequence";

    @Id
    @Indexed(unique = true)
    private Integer id;
    @Field("id_usuario")
    private Integer idUsuario;
    @Field("id_roles")
    private List<Integer> idRol;

}
