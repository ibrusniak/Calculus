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
        assertEquals(1, processor.compare(op1, op2));
        assertEquals(-1, processor.compare(op2, op1));
    }

    @Test
    public void compareTest2() {

        op1.clear();
        op2.clear();

        op1.addLast("-");
        op1.addLast("0");
        op2.addLast("0");
        assertEquals(0, processor.compare(op1, op2));

        op1.clear();
        op2.clear();

        op1.addLast("-");
        op1.addLast("1");
        op2.addLast("0");
        assertEquals(-1, processor.compare(op1, op2));

        op1.clear();
        op2.clear();

        op1.addLast("5");
        op2.addLast("6");
        assertEquals(-1, processor.compare(op1, op2));

        op1.clear();
        op2.clear();

        op1.addLast("6");
        op2.addLast("-");
        op2.addLast("4");
        assertEquals(1, processor.compare(op1, op2));
    }
}
