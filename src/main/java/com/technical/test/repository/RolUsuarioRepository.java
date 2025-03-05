package com.technical.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.technical.test.entity.RolUsuario;

@Repository
public interface RolUsuarioRepository extends MongoRepository<RolUsuario, Integer> {

    RolUsuario findByIdUsuario(Integer idUsuario);

}
