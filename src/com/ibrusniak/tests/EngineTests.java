package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.ibrusniak.core.Engine;

public class EngineTests {
    
    Engine engine = new Engine();

    @Test
    public void test1() {

        engine = new Engine();
        assertEquals("[]",  engine.toString());
        engine.keyPress(Engine.Key.D1);
        assertEquals("[1]",  engine.toString());
        engine.keyPress(Engine.Key.D2);
        assertEquals("[1, 2]",  engine.toString());
    }
}
