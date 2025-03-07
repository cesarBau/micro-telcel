package com.technical.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.technical.test.entity.Rol;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends MongoRepository<Rol, Integer> {

    @SuppressWarnings("null")
    Optional<Rol> findById(Integer id);

    List<Rol> findByNombre(String nombre);

}
