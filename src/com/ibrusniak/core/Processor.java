package com.ibrusniak.core;

import java.util.ArrayDeque;

/**
 * Processor. Operates with "numbers"
 * 
 * Addition: augend + addend = Sum.
 * Subtraction: Minuend - Subtrahend = Difference.
 * Multiplication: Multiplicand × Multiplier = Product. Generally, operands are called factors.
 * Division: Dividend ÷ Divisor = Quotient.
 * 
 * Modulation: Dividend % Divisor = Remainder.
 * Exponentiation: Base ^ Exponent = ___.
 * Finding roots: Degree √ Radicand = Root.
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
     * 
     * Addition: augend + addend = sum.
     * 
     * Rules:
     * 1. a + b = a + b
     * 2. a + (-b) = a - b
     * 3. (-a) + b = b - a
     * 4. (-a) + (-b) = -(a + b)
     * 
     * @param augend - ArrayDeque<String>
     * @param addend - ArrayDeque<String>
     * @return ArrayDeque<String>
     */
    public ArrayDeque<String> makeAddition(ArrayDeque<String> augend, ArrayDeque<String> addend) {

        ArrayDeque<String> sum = new ArrayDeque<>();

        // Rule 1
        if (!negative(addend) && !negative(addend)) sum = makeModuloAddition(augend, addend);

        // Rule 2
        if (!negative(augend) && negative(addend)) sum = makeSubtraction(augend, addend);

        // Rule 3
        if (negative(augend) && !negative(addend)) sum = makeSubtraction(addend, augend);

        // Rule 4
        if (negative(augend) && negative(addend)) {
            sum = makeModuloAddition(addend, augend);
            sum.addFirst("-");
        }

        return sum;
    }

    private ArrayDeque<String> makeModuloAddition(ArrayDeque<String> augend, ArrayDeque<String> addend) {

        ArrayDeque<String> sum = new ArrayDeque<>();

        ArrayDeque<String> op1 = new ArrayDeque<>(augend);
        ArrayDeque<String> op2 = new ArrayDeque<>(addend);
        
        prepareBoth(op1, op2);

        String op1Digit, op2Digit;
        Integer curentStepExcess = 0;
        while ((op1Digit = op1.pollLast()) != null) {

            if (op1Digit.equals(".")) {
                sum.addFirst(".");
                op2.pollLast();
                continue;
            }
            op2Digit = op2.pollLast();
            Integer digitSum = Integer.valueOf(op1Digit) + Integer.valueOf(op2Digit);
            if (curentStepExcess > 0) {
                digitSum += curentStepExcess;
                curentStepExcess = 0;
            }
            if (digitSum >= 10) {
                curentStepExcess = digitSum / 10;
                digitSum = digitSum - 10;
            }
            sum.addFirst(String.valueOf(digitSum));
        }
        if (curentStepExcess > 0)
            sum.addFirst(String.valueOf(curentStepExcess));

        removeRedundandDigits(sum);
        return sum;
    }

    /**
     *  1. a - b = a - b
     *  2. a - (-b) = a + b
     *  3. (-a) - (-b) = -a + b
     *  4. (-a) - b = -(a + b)
     * 
     * @param minuend
     * @param subtrahend
     * @return
     */
    public ArrayDeque<String> makeSubtraction(ArrayDeque<String> minuend, ArrayDeque<String> subtrahend) {

        ArrayDeque<String> difference = new ArrayDeque<>();
        return difference;
    }

    public ArrayDeque<String> makeMultiplication(ArrayDeque<String> operand1, ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();
        return result;
    }

    public ArrayDeque<String> makeDivision(ArrayDeque<String> operand1, ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();
        return result;
    }

    public int compare(ArrayDeque<String> operand1, ArrayDeque<String> operand2) {

        ArrayDeque<String> op1 = new ArrayDeque<>(operand1);
        ArrayDeque<String> op2 = new ArrayDeque<>(operand2);

        prepareBoth(op1, op2);

        int weight1 = 0, weight2 = 0, counter = 1;
        String s1 = "", s2 = "";
        while ((s1 = op1.pollLast()) != null) {

            s2 = op2.pollLast();
            if (s1.equals(".")) {
                continue;
            }
            weight1 += Integer.valueOf(s1) * counter;
            weight2 += Integer.valueOf(s2) * counter;
            counter++;
        }

        if (operand1.contains("-")) weight1 *= -1;
        if (operand2.contains("-")) weight2 *= -1;
        return Integer.compare(weight1, weight2);
    }

    /**
     * Prepare mean arrange both "numbers". Example:
     * 0.23, 233 -> 000.23, 233.00
     * 
     * @param arg1 - ArrayDeque<String>
     * @param arg2 - ArrayDeque<String>
     */
    private void prepareBoth(ArrayDeque<String> arg1, ArrayDeque<String> arg2) {

        makeModulo(arg1);
        makeModulo(arg2);
        normalize(arg1);
        normalize(arg2);
        widenNumbers(arg1, arg2);
    }

    private void makeModulo(ArrayDeque<String> operand) {
        operand.removeFirstOccurrence("-");
    }

    /**
     * Transfom "digit sequence": 0 -> 0.0, 0. -> 0.0
     * 
     * @param arg - ArrayDeque<String> - the "number"
     */
    private void normalize(ArrayDeque<String> arg) {

        if (!arg.contains(".")) {
            arg.addLast(".");
            arg.addLast("0");
            return;
        }
        
        if (arg.getLast().equals(".")) {
            arg.addLast("0");
            return;
        }
    }

    private void widenNumbers(ArrayDeque<String> arg1, ArrayDeque<String> arg2) {

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
    }

    private String numToString(ArrayDeque<String> arg) {
        return arg.stream().reduce((e1, e2) -> e1 + e2).get();
    }

    private void removeRedundandDigits(ArrayDeque<String> arg) {

        while(arg.getLast().equals("0"))
            arg.removeLast();
        if (arg.getLast().equals("."))
            arg.removeLast();
    }

    private ArrayDeque<String> zero() {

        ArrayDeque<String> result = new ArrayDeque<>();
        result.addLast("0");
        return result;
    }

    private boolean negative(ArrayDeque<String> arg) {
        return (compare(arg, zero()) == -1);
    }
}
