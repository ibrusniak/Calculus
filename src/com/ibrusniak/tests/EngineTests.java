package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;

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
    public void numberInputTest1() {

        engine = new Engine();

        engine.keyPressed("0");
        engine.keyPressed("0");
        engine.keyPressed("0");
        assertEquals("0", engine.toString());
        engine.keyPressed(Engine.Key.D5);
        assertEquals("5", engine.toString());
        engine.keyPressed("2");
        engine.keyPressed("0");
        assertEquals("520", engine.toString());
        // engine.keyPressed();
    }
}
