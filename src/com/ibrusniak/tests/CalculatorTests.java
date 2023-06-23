package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;

import org.junit.experimental.theories.suppliers.TestedOn;
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
    public void resultTest1() {
        
        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.input("=");
        assertEquals("0", calculator.toString());
        calculator.input("= = =");
        assertEquals("0", calculator.toString());
    }
    
    @Test
    public void resultTest2() {
        
        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.input("=");
        assertEquals("0", calculator.toString());
        calculator.input("2 4");
        assertEquals("24", calculator.toString());
        calculator.input("=");
        assertEquals("24", calculator.toString());
        calculator.input("1 5");
        assertEquals("15", calculator.toString());
        calculator.input("=");
        assertEquals("15", calculator.toString());
        calculator.input("= = =");
        assertEquals("15", calculator.toString());
    }


    @Test
    public void clearTest1() {
        
        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.input("2 0 0");
        assertEquals("200", calculator.toString());
        calculator.keyPressed("C");
        assertEquals("0", calculator.toString());
    }

    @Test
    public void clearEntryTest1() {
        
        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.input("2 0 0");
        assertEquals("200", calculator.toString());
        calculator.keyPressed("CE");
        assertEquals("0", calculator.toString());
    }

    @Test
    public void numberInputTest1() {

        calculator = new Calculator();

        calculator.input("0 0 0");
        assertEquals("0", calculator.toString());
        calculator.input("5");
        assertEquals("5", calculator.toString());
        calculator.input("2 0");
        assertEquals("520", calculator.toString());
        calculator.input(".");
        assertEquals("520.", calculator.toString());
        calculator.input("0");
        assertEquals("520.0", calculator.toString());
        calculator.input("0 0");
        assertEquals("520.000", calculator.toString());
        calculator.input("9");
        assertEquals("520.0009", calculator.toString());
        calculator.input("+/-");
        assertEquals("-520.0009", calculator.toString());
        calculator.input("+/-");
        assertEquals("520.0009", calculator.toString());
        calculator.input("+/-");
        assertEquals("-520.0009", calculator.toString());
    }

    @Test
    public void numberInputTest2() {

        calculator = new Calculator();

        assertEquals("0", calculator.toString());
        calculator.input(".");
        assertEquals("0.", calculator.toString());
        calculator.input("0");
        assertEquals("0.0", calculator.toString());
        calculator.input("0");
        assertEquals("0.00", calculator.toString());
        calculator.input("0");
        assertEquals("0.000", calculator.toString());
        calculator.input("0");
        assertEquals("0.0000", calculator.toString());
        calculator.input("3");
        assertEquals("0.00003", calculator.toString());
        calculator.input("3");
        assertEquals("0.000033", calculator.toString());
        calculator.input("3");
        assertEquals("0.0000333", calculator.toString());
        calculator.input("9");
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

        calculator.input("2 + 3 =");
        assertEquals("5", calculator.toString());
		
        calculator.input("C");
        calculator.input("9 + 3 =");
        assertEquals("12", calculator.toString());
        
		calculator.input("C");
        calculator.input("9 9 + 9 9 =");
        assertEquals("198", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 0 0 + 3 0 0 =");
        assertEquals("500", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 0 9 9 + 3 0 0 =");
        assertEquals("2399", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 0 9 9 . 0 0 0 3 + 3 0 0 =");
        assertEquals("2399.0003", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 0 9 9 . 0 0 0 3 + 3 0 0 . 3 =");
        assertEquals("2399.3003", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 9 2 + 9 8 9 =");
        assertEquals("1281", calculator.toString());
        
		calculator.input("C");
        calculator.input("3 4 9 2 . 0 2 3 + 9 8 9 =");
        assertEquals("4481.023", calculator.toString());
        
		calculator.input("C");
        calculator.input("3 4 9 2 . 9 2 3 0 3 + 9 8 9 . 9 9 9 =");
        assertEquals("4482.92203", calculator.toString());
        
		calculator.input("C");
        calculator.input("1 0 0 0 + 0 . 0 0 0 0 0 4 =");
        assertEquals("1000.000004", calculator.toString());
    }
    
    @Test
    public void additionTest2() {
        
        calculator = new Calculator();
        
        calculator.input("5 +");
        assertEquals("5", calculator.toString());
        
		calculator.input("C");
        calculator.input("5 + 6 =");
        assertEquals("11", calculator.toString());
        
		calculator.input("C");
        calculator.input("5 + 6 +");
        assertEquals("11", calculator.toString());
        
		calculator.input("C");
        calculator.input("5 + 6 + 9 +");
        assertEquals("20", calculator.toString());
        
		calculator.input("C");
        calculator.input("3 2 + 1 9 + 4 1 =");
        assertEquals("92", calculator.toString());
        
		calculator.input("C");
        calculator.input("2 + 3 + 5 =");
        assertEquals("10", calculator.toString());
    }

    @Test
    public void errorStateTest1() {

        calculator = new Calculator();

        calculator.input("9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9");
        calculator.input("+");
        calculator.input("9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9");
        calculator.input("=");
        assertEquals("ERROR", calculator.toString());
        calculator.input("5 5 + 1 0 0 =");
        assertEquals("ERROR", calculator.toString());
        calculator.input("CE");
        assertEquals("ERROR", calculator.toString());
        calculator.input("C");
        assertEquals("0", calculator.toString());
    }
}
