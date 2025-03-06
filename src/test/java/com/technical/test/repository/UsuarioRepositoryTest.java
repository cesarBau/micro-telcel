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

import com.technical.test.entity.Usuario;

@Testcontainers
@SpringBootTest
public class UsuarioRepositoryTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "testdb");
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario initEntity;

    @BeforeEach
    void setUp() {
        initEntity = new Usuario();
        initEntity.setId(1);
        initEntity.setNombre("test");
        initEntity.setAPaterno("testAP");
        initEntity.setAMaterno("testAM");
        initEntity.setIdTrole(1);
        usuarioRepository.save(initEntity);
    }

    @Test
    void testGetAllUser() {
        List<Usuario> data = usuarioRepository.findAll();
        assertEquals(1, data.size());
    }

    @Test
    void testCreateUser() {
        Usuario entity = new Usuario();
        entity.setId(2);
        entity.setNombre("testEntity");
        entity.setAPaterno("testAP");
        entity.setAMaterno("testAM");
        entity.setIdTrole(1);
        Usuario data = usuarioRepository.save(entity);
        assertNotNull(data);
    }

    @Test
    void testGetuserById() {
        Integer id = 1;
        Optional<Usuario> data = usuarioRepository.findById(id);
        assertEquals(id, data.get().getId());
    }

    @Test
    void testGetuserByName() {
        String name = "test";
        List<Usuario> data = usuarioRepository.findByNombre(name);
        assertEquals(1, data.size());
    }

    @Test
    void testDeleteUser() {
        Integer id = 1;
        usuarioRepository.deleteById(id);
        Optional<Usuario> data = usuarioRepository.findById(id);
        assertEquals(Optional.empty(), data);
    }
}
