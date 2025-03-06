package com.technical.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.technical.test.entity.RolUsuario;

@Testcontainers
@SpringBootTest
public class RolUsuarioRepositoryTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    private RolUsuario initEntity;
    private List<Integer> idRol;

    @BeforeEach
    void setUp() {
        idRol = new ArrayList<>();
        idRol.add(1);
        initEntity = new RolUsuario();
        initEntity.setId(1);
        initEntity.setIdUsuario(1);
        initEntity.setIdRol(idRol);
        rolUsuarioRepository.save(initEntity);
    }

    @Test
    void testGetAllRolUser() {
        List<RolUsuario> data = rolUsuarioRepository.findAll();
        assertEquals(1, data.size());
    }

    @Test
    void testCreateRolUser() {
        List<Integer> dataRol = new ArrayList<>();
        dataRol.add(1);
        RolUsuario entity = new RolUsuario();
        entity.setId(2);
        entity.setIdUsuario(2);
        entity.setIdRol(idRol);
        RolUsuario response = rolUsuarioRepository.save(entity);
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
    }

    @Test
    void testGetRolUserById() {
        Integer id = 1;
        Optional<RolUsuario> data = rolUsuarioRepository.findById(id);
        assertEquals(id, data.get().getId());
    }

    @Test
    void testGetRolUserByIdUser() {
        Integer id = 1;
        RolUsuario data = rolUsuarioRepository.findByIdUsuario(id);
        assertEquals(id, data.getIdUsuario());
    }

    @Test
    void testDeleteRolUser() {
        Integer id = 2;
        rolUsuarioRepository.deleteById(id);
        Optional<RolUsuario> data = rolUsuarioRepository.findById(id);
        assertEquals(Optional.empty(), data);
    }

}
