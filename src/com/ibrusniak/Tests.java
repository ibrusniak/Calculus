package com.ibrusniak;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class Tests {
    
    @Test
    public void test1() {
        assertEquals(new Num(0).equals(new Num(0)), true);
    }
    
    @Test
    public void test2() {
        assertEquals(new Num(-0).equals(new Num(0)), true);
    }
    
    @Test
    public void test3() {
        assertEquals(new Num(0).equals(new Num(-0)), true);
    }
}