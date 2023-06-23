package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.Collections;

import org.junit.Test;

import com.ibrusniak.core.Processor;

public class ProcessorTests {
    
    Processor processor = new Processor();

    ArrayDeque<String> op1 = new ArrayDeque<>();
    ArrayDeque<String> op2 = new ArrayDeque<>();

    @Test
    public void compareTest1() {

        op1.clear();
        op2.clear();

        Collections.addAll(op1, "2", "3");
        Collections.addAll(op2, "2", "0");
        assertEquals(1, processor.compare(op2, op1));
        assertEquals(-1, processor.compare(op1, op2));
    }
}
