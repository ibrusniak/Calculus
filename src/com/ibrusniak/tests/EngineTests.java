package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import com.ibrusniak.core.Engine;

public class EngineTests {
    
    Engine engine = new Engine();

    @Test
    public void initialStateTest() {

        engine = new Engine();
        assertEquals("0", engine.toString());
    }
    
    @Test
    public void clearTest1() {
        
        engine = new Engine();
        assertEquals("0", engine.toString());
        engine.keyPressed("1");
        engine.keyPressed("2");
        engine.keyPressed("3");
        assertEquals("123", engine.toString());
        engine.keyPressed("C");
        assertEquals("0", engine.toString());
    }

    @Test
    public void numberInputTest1() {

        engine = new Engine();

        engine.keyPressed("0");
        engine.keyPressed("0");
        engine.keyPressed("0");
        assertEquals("0", engine.toString());
        engine.keyPressed("5");
        assertEquals("5", engine.toString());
        engine.keyPressed("2");
        engine.keyPressed("0");
        assertEquals("520", engine.toString());
        engine.keyPressed(".");
        assertEquals("520.", engine.toString());
        engine.keyPressed("0");
        assertEquals("520.0", engine.toString());
        engine.keyPressed("0");
        engine.keyPressed("0");
        assertEquals("520.000", engine.toString());
        engine.keyPressed("9");
        assertEquals("520.0009", engine.toString());
        engine.keyPressed("+/-");
        assertEquals("-520.0009", engine.toString());
        engine.keyPressed("+/-");
        assertEquals("520.0009", engine.toString());

        
    }
}
