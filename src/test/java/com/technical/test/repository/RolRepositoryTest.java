package com.technical.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.technical.test.entity.Rol;

@Testcontainers
@SpringBootTest
public class RolRepositoryTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @Autowired
    private RolRepository rolRepository;

    private Rol initEntity;

    @BeforeEach
    void setUp() {
        initEntity = new Rol();
        initEntity.setId(1);
        initEntity.setNombre("test");
        rolRepository.save(initEntity);
    }

    @Test
    void testGetAllRol() {
        List<Rol> response = rolRepository.findAll();
        assertEquals(1, response.size());
    }

    @Test
    void testCreateRol() {
        Rol data = new Rol();
        data.setId(2);
        data.setNombre("Nombre");
        Rol response = rolRepository.save(data);
        assertNotNull(response);
    }

    @Test
    void testGetRolByI() {
        Integer id = 1;
        Optional<Rol> response = rolRepository.findById(id);
        assertEquals(initEntity.getId(), response.get().getId());
    }

    @Test
    void testGetRolByName() {
        String name = "test";
        List<Rol> response = rolRepository.findByNombre(name);
        assertEquals(1, response.size());
    }

    @Test
    void testDeleteById() {
        Integer id = 2;
        rolRepository.deleteById(id);
        Optional<Rol> response = rolRepository.findById(id);
        assertEquals(Optional.empty(), response);
    }

}
