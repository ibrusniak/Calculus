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

    public int compare(ArrayDeque<String> operand1, ArrayDeque<String> operand2) {

        ArrayDeque<String> op1 = new ArrayDeque<>(operand1);
        ArrayDeque<String> op2 = new ArrayDeque<>(operand2);

        widenNumbers(op1, op2);

        if (!op1.contains("-") && op2.contains("-")) return 1;
        if (op1.contains("-") && !op2.contains("-")) return -1;
        if (op1.contains("-") && op2.contains("-")) {
            return -moduleCompare(op1, op2);
        }
        return moduleCompare(op1, op2);
    }

    private int moduleCompare(ArrayDeque<String> operand1, ArrayDeque<String> operand2) {

        ArrayDeque<String> op1 = new ArrayDeque<>(operand1);
        ArrayDeque<String> op2 = new ArrayDeque<>(operand2);

        op1.removeFirstOccurrence("-");
        op2.removeFirstOccurrence("-");

        String nextOp1 = "", nextOp2 = "";
        while ((nextOp1 = op1.pollFirst()) != null) {

            nextOp2 = op2.pollFirst();
            if (nextOp1.equals(".")) continue;
            if (Integer.valueOf(nextOp1) > Integer.valueOf(nextOp2)) {
                return 1;
            } else if (Integer.valueOf(nextOp1) < Integer.valueOf(nextOp2)) {
                return -1;
            }
        }
        return 0;
    }

    public void widenNumbers(ArrayDeque<String> argument1, ArrayDeque<String> argument2) {

        ArrayDeque<String> arg1 = normalize(argument1);
        ArrayDeque<String> arg2 = normalize(argument2);

        arg1.removeFirstOccurrence("-");
        arg2.removeFirstOccurrence("-");

        String arg1String = numToString(arg1);
        String arg2String = numToString(arg2);

        int arg1IntegerPartLength = arg1String.split("\\.")[0].length();
        int arg2IntegerPartLength = arg2String.split("\\.")[0].length();

        if (arg1IntegerPartLength > arg2IntegerPartLength) {
            do {
                arg2.addFirst("0");
            } while (++arg2IntegerPartLength < arg1IntegerPartLength);
        } else if (arg1IntegerPartLength < arg2IntegerPartLength) {
            do {
                arg1.addFirst("0");
            } while (++arg1IntegerPartLength < arg2IntegerPartLength);
        }

        int arg1FracPartLength = arg1String.split("\\.")[1].length();
        int arg2FracPartLength = arg2String.split("\\.")[1].length();

        if (arg1FracPartLength < arg2FracPartLength) {
            do {
                arg1.addLast("0");
            } while (++arg1FracPartLength < arg2FracPartLength);
        } else if (arg1FracPartLength > arg2FracPartLength) {
             do {
                arg2.addLast("0");
            } while (++arg2FracPartLength < arg1FracPartLength);
        }

        boolean arg1HasSign = argument1.contains("-");
        boolean arg2HasSign = argument2.contains("-");
        argument1.clear();
        argument2.clear();
        arg1.stream().forEach(argument1::add);
        arg2.stream().forEach(argument2::add);
        if (arg1HasSign && !arg1.stream().reduce((a,b)->a+b).get().equals("0.0")) argument1.addFirst("-");
        if (arg2HasSign && !arg2.stream().reduce((a,b)->a+b).get().equals("0.0")) argument2.addFirst("-");
    }

    /**
     * Transfom "number": 0 -> 0.0, 0. -> 0.0, .0 -> 0.0
     * -0 -> -0.0, -0. -> -0.0, -.0 -> 0.0
     * 
     * @param number - ArrayDeque<String> - the "number"
     */
    public ArrayDeque<String> normalize(ArrayDeque<String> number) {

        // ArrayDeque<String> result = new ArrayDeque<>();
        ArrayDeque<String> result = new ArrayDeque<>(number);

        // result = new ArrayDeque<>(number);
        result.removeFirstOccurrence("-");
        result.removeFirstOccurrence("+");
        result.removeFirstOccurrence("");
        if (result.isEmpty() || result.stream().filter(a -> !a.equals("+") && !a.equals("-") && !a.equals(".")).allMatch(a->a.equals("0"))) {
            result.clear();
            result.add("0");
            result.add(".");
            result.add("0");
            return result;
        }
        
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

    private String numToString(ArrayDeque<String> number) {
        return number.stream().reduce((e1, e2) -> e1 + e2).get();
    }
}
