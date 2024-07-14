package io.deeplay.camp.engine.entities;

import io.deeplay.camp.engine.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(5);
    }

    @Test
    void testFieldSize() {
        assertEquals(5, field.getSize(), "Field size = 5");
    }

    @Test
    void testFieldIsGenerated() {
        assertNotNull(field.getBoard(), "Field board not be null");
        assertEquals(5, field.getBoard().length, "Field board size = 5x5");
        assertEquals(5, field.getBoard()[0].length, "Field board size = 5x5");
    }
}