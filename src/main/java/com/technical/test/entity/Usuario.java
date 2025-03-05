package com.technical.test.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TUSUARIO")
public class Usuario {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    Integer id;
    @Field("NOMBRE")
    String nombre;
    @Field("A_PATERNO")
    String aPaterno;
    @Field("A_MATERNO")
    String aMaterno;
    @Field("ID_TROLE")
    Integer idTrole;

}
