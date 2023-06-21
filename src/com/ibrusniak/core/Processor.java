package com.ibrusniak.core;

import java.util.ArrayDeque;

public class Processor {
    
    public ArrayDeque<String> makeAddition(final ArrayDeque<String> operand1, final ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();

        ArrayDeque<String> op1 = new ArrayDeque<>(operand1);
        ArrayDeque<String> op2 = new ArrayDeque<>(operand2);
        
        prepareBoth(op1, op2);

        return result;
    }

    public ArrayDeque<String> makeSubtraction(final ArrayDeque<String> operand1, final ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();
        return result;
    }

    public ArrayDeque<String> makeMultiplication(final ArrayDeque<String> operand1, final ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();
        return result;
    }

    public ArrayDeque<String> makeDivision(final ArrayDeque<String> operand1, final ArrayDeque<String> operand2) {

        ArrayDeque<String> result = new ArrayDeque<>();
        return result;
    }

    private void makeModulo(final ArrayDeque<String> operand) {

        operand.removeFirstOccurrence("-");
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
        widenBoth(arg1, arg2);
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

    private void widenBoth(ArrayDeque<String> arg1, ArrayDeque<String> arg2) {

        String arg1String = aDToString(arg1);
        String arg2String = aDToString(arg2);

        int arg1IntegerPartLength = arg1String.split("\\.")[0].length();
        int arg2IntegerPartLength = arg2String.split("\\.")[0].length();

        if (arg1IntegerPartLength > arg2IntegerPartLength) {
            do {
                arg1.addFirst("0");
            } while (arg2IntegerPartLength++ < arg1IntegerPartLength);
        } else if (arg1IntegerPartLength < arg2IntegerPartLength) {
            do {
                arg1.addFirst("0");
            } while (arg1IntegerPartLength++ < arg2IntegerPartLength);
        }

        int arg1FracPartLength = arg1String.split("\\.")[1].length();
        int arg2FracPartLength = arg2String.split("\\.")[1].length();

        if (arg1FracPartLength < arg2FracPartLength) {
            do {
                arg1.addLast("0");
            } while (arg1FracPartLength++ < arg2FracPartLength);
        } else if (arg1FracPartLength > arg2FracPartLength) {
             do {
                arg1.addLast("0");
            } while (arg2FracPartLength++ < arg1FracPartLength);
        }
    }

    private String aDToString(ArrayDeque<String> arg) {
        
        return arg.stream().reduce((e1, e2) -> e1 + e2).get();
    }
}
