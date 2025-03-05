package com.technical.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.technical.test.entity.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, Integer> {

    List<Usuario> findByNombre(String nombre);

}
