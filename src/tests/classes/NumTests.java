package tests.classes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.classes.Num;

public class NumTests {
    
    @Test
    public void constructorTest1() {

        new Num(0);
        new Num(100);
        new Num(-0);
        new Num(-100);
        new Num(00);
        new Num(-00);
    }

    @Test
    public void constructorTest2() {

        new Num(0.0);
        new Num(.0);
        new Num(-0.0);
        new Num(.0);
        new Num(-.0);
        new Num(2.5);
        new Num(-2.5);
    }

    @Test
    public void toStringTest1() {

        String expected = "0";
        assertEquals(expected, new Num().toString());
        assertEquals(expected, new Num(0).toString());
        assertEquals(expected, new Num(00).toString());
        assertEquals(expected, new Num(0000).toString());
        assertEquals(expected, new Num(-0).toString());
        assertEquals(expected, new Num(-000).toString());
        assertEquals(expected, new Num(0.0).toString());
        assertEquals(expected, new Num(.0).toString());
        assertEquals(expected, new Num(0.000).toString());
        assertEquals(expected, new Num(000.).toString());
        assertEquals(expected, new Num(-0.0).toString());
        assertEquals(expected, new Num(-.0).toString());
        assertEquals(expected, new Num(-0.0000).toString());
        assertEquals(expected, new Num(-000.).toString());
    }
    
    @Test
    public void toStringTest2() {
        
        String expected = "100";
        assertEquals(expected, new Num(100).toString());
        assertEquals(expected, new Num(100.00).toString());
        assertEquals(expected, new Num(100.00000).toString());
        
        expected = "-100";
        assertEquals(expected, new Num(-100).toString());
        assertEquals(expected, new Num(-100.00).toString());
        assertEquals(expected, new Num(-100.00000).toString());
        
        expected = "100.00002";
        assertEquals(expected, new Num(100.00002).toString());
        assertEquals(expected, new Num(100.00002000).toString());
        assertEquals(expected, new Num(100.00002000000000000).toString());
        assertEquals(expected, new Num(10000002e-5).toString());
        
        expected = "-100.00002";
        assertEquals(expected, new Num(-100.00002).toString());
        assertEquals(expected, new Num(-10000002e-5).toString());
    }
}