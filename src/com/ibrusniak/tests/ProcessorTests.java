package com.ibrusniak.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.Arrays;

import org.junit.Test;

import com.ibrusniak.core.Processor;

public class ProcessorTests {
    
    Processor processor = new Processor();

    ArrayDeque<String> op1 = new ArrayDeque<>();
    ArrayDeque<String> op2 = new ArrayDeque<>();

    /*
     * valid numbers:
     * 
     *      '0', '2', '34'? '1', '8', '10', '-1'
     *      '0.1', '-0.1', '-2.884', '114.411554',
     *      '0.0000045', '-444.00001', '-0.001',
     *      '-44', '256.654'
     * 
     */
    @Test
    public void isValidNumberTest1() {

        refillFromString(op1, "0");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "2");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "34");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "1");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "8");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "10");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "-1");
        assertTrue(processor.isValidNumber(op1));

        refillFromString(op1, "0.1");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "-0.1");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "-2.884");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "114.411554");
        assertTrue(processor.isValidNumber(op1));
        
        refillFromString(op1, "0.0000045");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "-444.00001");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "-0.001");
        assertTrue(processor.isValidNumber(op1));

        refillFromString(op1, "-44");
        assertTrue(processor.isValidNumber(op1));
        refillFromString(op1, "256.654");
        assertTrue(processor.isValidNumber(op1));

    }

    /*
     * invalid numbers:
     * 
     *      '1.0', '--2.2', '2.33-', '02344', '00',
     *      '00001.223', '259.', '0.', '-1.0000',
     *      '-44.', '0.000', '-0.00', '00.1', '+0',
     *      '++2', '-0', '0.0', '01', '-01',
     *      '+2', '+34'
     * 
     */
    @Test
    public void isValidNumberTest2() {

        refillFromString(op1, "1.0");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "--2.2");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "2.33-");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "02344");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "00");
        assertFalse(processor.isValidNumber(op1));

        refillFromString(op1, "00001.223");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "259.");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "0.");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "-1.0000");
        assertFalse(processor.isValidNumber(op1));

        refillFromString(op1, "-44.");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "0.000");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "-0.00");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "00.1");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "+0");
        assertFalse(processor.isValidNumber(op1));

        refillFromString(op1, "++2");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "-0");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "0.0");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "01");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "-01");
        assertFalse(processor.isValidNumber(op1));

        refillFromString(op1, "+2");
        assertFalse(processor.isValidNumber(op1));
        refillFromString(op1, "+34");
        assertFalse(processor.isValidNumber(op1));
        
    }

    @Test
    public void normalizeTest1() {

        op1 = new ArrayDeque<>();
        String normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "0.");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "0.0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "0.000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "000.000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "-0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "-000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "-0.");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "-0.0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "-0.000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "+0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "+0.0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "+.0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);

        refillFromString(op1, "+00.0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);
    }

    @Test
    public void normalizeTest2() {

        refillFromString(op1, "5");
        String normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("5.0", normalized);

        refillFromString(op1, "-5");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-5.0", normalized);

        refillFromString(op1, "5.0000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("5.0", normalized);

        refillFromString(op1, "00005.0000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("5.0", normalized);

        refillFromString(op1, "-050.0002000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-50.0002", normalized);

        refillFromString(op1, "-.005000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-0.005", normalized);

        refillFromString(op1, "500");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("500.0", normalized);

        refillFromString(op1, "-5050");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-5050.0", normalized);

        refillFromString(op1, "00005000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("5000.0", normalized);

        refillFromString(op1, "-0005.0010000");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-5.001", normalized);

        refillFromString(op1, "-0.0005");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("-0.0005", normalized);

        refillFromString(op1, "0");
        normalized = arrayDequeToString(processor.normalize(op1));
        assertEquals("0.0", normalized);
    }

    @Test
    public void widenNumbersTest1() {

        refillFromString(op1, "5");
        refillFromString(op2, "0.1");
        processor.widenNumbers(op1, op2);
        assertEquals("5.0", arrayDequeToString(op1));
        assertEquals("0.1", arrayDequeToString(op2));

        refillFromString(op1, "50000");
        refillFromString(op2, "0.1");
        processor.widenNumbers(op1, op2);
        assertEquals("50000.0", arrayDequeToString(op1));
        assertEquals("00000.1", arrayDequeToString(op2));

        refillFromString(op1, "-1655");
        refillFromString(op2, "0.1");
        processor.widenNumbers(op1, op2);
        assertEquals("-1655.0", arrayDequeToString(op1));
        assertEquals("0000.1", arrayDequeToString(op2));

        refillFromString(op1, "-1500");
        refillFromString(op2, "-0.10001");
        processor.widenNumbers(op1, op2);
        assertEquals("-1500.00000", arrayDequeToString(op1));
        assertEquals("-0000.10001", arrayDequeToString(op2));
    }

    private void refillFromString(ArrayDeque<String> op, String str) {
        op.clear();
        Arrays.stream(str.split("")).forEach(op::add);
    }

    private String arrayDequeToString(ArrayDeque<String> op) {
        return op.stream().reduce((a, b) -> a + b).get();
    }
}
