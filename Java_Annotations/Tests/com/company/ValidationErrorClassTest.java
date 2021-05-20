/*
 * @author <a href="arbenmustafa@edu.hse.ru"> Ben Mustafa Anas</a>
 */

package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorClassTest {

    @Test
    void getMessage() {
        ValidationError error = new ValidationErrorClass("TestMessage", "Path", null);
        assertEquals("TestMessage", error.getMessage());
    }

    @Test
    void getPath() {
        ValidationError error = new ValidationErrorClass("TestMessage", "Path", null);
        assertEquals("Path", error.getPath());
    }

    @Test
    void getFailedValue() {
        ValidationError error = new ValidationErrorClass("TestMessage", "Path", null);
        assertEquals(null, error.getFailedValue());
    }
}