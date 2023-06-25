package com.ibrusniak.core;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * Processor. Operates with "numbers"
 * 
 * "Number" is digit sequence, maybe with preceding minus
 * Examples:
 *  "0"
 *  "0.0"
 *  "1.0"
 *  "-0.0"
 *  "1"
 *  "-1"
 *  "-1.0"
 * 
 * Addition: augend + addend = Sum.
 * Subtraction: Minuend - Subtrahend = Difference.
 * Multiplication: Multiplicand × Multiplier = Product. Generally, operands are called factors.
 * Division: Dividend ÷ Divisor = Quotient.
 * 
 * Rules of the arithmetic:
 * Addition:
 *  a + b = a + b
 *  a + (-b) = a - b
 *  (-a) + b = b - a
 *  (-a) + (-b) = -(a + b)
 * Substraction:
 *  a - b = a - b
 *  a - (-b) = a + b
 *  (-a) - (-b) = -a + b
 *  (-a) - b = -(a + b)
 * 
 */
public class Processor {

    /**
     * Transfom "number": 0 -> 0.0, 0. -> 0.0, .0 -> 0.0
     * -0 -> -0.0, -0. -> -0.0, -.0 -> 0.0
     * 
     * @param number - ArrayDeque<String> - the "number"
     */
    public ArrayDeque<String> normalize(ArrayDeque<String> number) {

        ArrayDeque<String> result = new ArrayDeque<>();
        if (number.isEmpty() || number.stream().filter(a -> !a.equals("+") && !a.equals("-") && !a.equals(".")).allMatch(a->a.equals("0"))) {
            result.add("0");
            result.add(".");
            result.add("0");
            return result;
        }

        result = new ArrayDeque<>(number);
        result.removeFirstOccurrence("-");
        result.removeFirstOccurrence("+");
        
        while (result.getFirst().equals("0")) result.removeFirst();
        if (!result.contains(".")) result.addLast(".");
        while (result.getLast().equals("0")) result.removeLast();
        
        if (result.getFirst().equals(".")) result.addFirst("0");
        if (result.getLast().equals(".")) result.addLast("0");

        if (number.contains("-")) result.addFirst("-");
        return result;
    }

    /**
     * Check if number is valid sequence
     * valid numbers:
     *      '0', '2', '34' '1', '8', '10', '-1'
     *      '0.1', '-0.1', '-2.884', '114.411554',
     *      '0.0000045', '-444.00001', '-0.001',
     *      '-44', '256.654'
     * invalid numbers:
     *      '1.0', '--2.2', '2.33-', '02344', '00',
     *      '00001.223', '259.', '0.', '-1.0000',
     *      '-44.', '0.000', '-0.00', '00.1', '+0',
     *      '++2', '-0', '0.0', '01', '-01',
     *      '+2', '+34'
     * 
     * @param number - testable number
     */
    public boolean isValidNumber(ArrayDeque<String> number) {

        String stringRepresentation = number.stream().reduce((a, b) -> a + b).get();
        String regexp = "^((-?(((0\\.|[1-9]+0*\\.)[1-9]*0*[1-9]+)|([1-9]+0*[1-9]*)))|(0))$";
        if (stringRepresentation != null &&
            stringRepresentation.matches(regexp)) return true;
        return false;
    }
}
