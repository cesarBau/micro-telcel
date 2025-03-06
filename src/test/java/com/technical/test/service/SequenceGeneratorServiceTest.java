package com.technical.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

import com.technical.test.entity.DatabaseSequence;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SequenceGeneratorServiceTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorService sequenceGenerator;

    private DatabaseSequence databaseSequence;

    @BeforeEach
    void setUp() {
        databaseSequence = new DatabaseSequence();
        databaseSequence.setId("test");
        databaseSequence.setSeq(1L);
    }

    @Order(1)
    @Test
    void testGenerateSequence() {
        String fiel = "test";
        Long data = sequenceGenerator.generateSequence(fiel);
        assertEquals(databaseSequence.getSeq(), data);
    }

}
