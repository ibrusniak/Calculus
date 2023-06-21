package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.ibrusniak.core.Calculator;

public class CalculatorTests {
    
    Calculator calculator = new Calculator();

    @Test
    public void initialStateTest() {

        calculator = new Calculator();

        assertEquals("0", calculator.toString());
    }
    
    @Test
    public void clearTest1() {
        
        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.keyPressed("1");
        calculator.keyPressed("2");
        calculator.keyPressed("3");
        assertEquals("123", calculator.toString());
        calculator.keyPressed("C");
        assertEquals("0", calculator.toString());
    }

    @Test
    public void numberInputTest1() {

        calculator = new Calculator();

        calculator.keyPressed("0");
        calculator.keyPressed("0");
        calculator.keyPressed("0");
        assertEquals("0", calculator.toString());
        calculator.keyPressed("5");
        assertEquals("5", calculator.toString());
        calculator.keyPressed("2");
        calculator.keyPressed("0");
        assertEquals("520", calculator.toString());
        calculator.keyPressed(".");
        assertEquals("520.", calculator.toString());
        calculator.keyPressed("0");
        assertEquals("520.0", calculator.toString());
        calculator.keyPressed("0");
        calculator.keyPressed("0");
        assertEquals("520.000", calculator.toString());
        calculator.keyPressed("9");
        assertEquals("520.0009", calculator.toString());
        calculator.keyPressed("+/-");
        assertEquals("-520.0009", calculator.toString());
        calculator.keyPressed("+/-");
        assertEquals("520.0009", calculator.toString());
        calculator.keyPressed("+/-");
        assertEquals("-520.0009", calculator.toString());
    }

    @Test
    public void numberInputTest2() {

        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.keyPressed(".");
        assertEquals("0.", calculator.toString());
        calculator.keyPressed("0");
        assertEquals("0.0", calculator.toString());
        calculator.keyPressed("0");
        assertEquals("0.00", calculator.toString());
        calculator.keyPressed("0");
        assertEquals("0.000", calculator.toString());
        calculator.keyPressed("0");
        assertEquals("0.0000", calculator.toString());
        calculator.keyPressed("3");
        assertEquals("0.00003", calculator.toString());
        calculator.keyPressed("3");
        assertEquals("0.000033", calculator.toString());
        calculator.keyPressed("3");
        assertEquals("0.0000333", calculator.toString());
        calculator.keyPressed("9");
        assertEquals("0.00003339", calculator.toString());
    }

    @Test
    public void inputTest1() {

        calculator = new Calculator();

        calculator.input(".");
        calculator.input("C");
        assertEquals("0", calculator.toString());
        
        calculator.input("CE C BS . 2 3 5 +/-");
        assertEquals("-0.235", calculator.toString());
    }
    
    @Test
    public void backspaceTest1() {
        
        calculator = new Calculator();
        
        calculator.input("BS BS BS");
        assertEquals("0", calculator.toString());
        calculator.input("1 7 8 9");
        assertEquals("1789", calculator.toString());
        calculator.input("BS");
        assertEquals("178", calculator.toString());
        calculator.input("BS");
        assertEquals("17", calculator.toString());
        calculator.input("BS");
        assertEquals("1", calculator.toString());
        calculator.input("BS");
        assertEquals("0", calculator.toString());
        calculator.input("BS");
        assertEquals("0", calculator.toString());
    }

    @Test
    public void additionTest1() {

        calculator = new Calculator();

        calculator.input("2 0 0 + 3 0 0 =");
        assertEquals("500", calculator.toString());
    }
}
