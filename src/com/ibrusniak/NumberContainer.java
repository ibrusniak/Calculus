package com.ibrusniak;

import java.util.Arrays;

public class NumberContainer {

    private boolean integerNumber = true;
    private Integer[] integerPart = new Integer[0];
    private Integer[] fractionalPart = new Integer[0];

    public void addDot() { integerNumber = false; }

    public NumberContainer addDigit(Integer digit) {

        if (integerNumber)
            addDigitToIntegerPart(digit);
        else
            addDigitToFractionalPart(digit);
        return this;
    }

    public NumberContainer backSpace() {
        
        if (integerNumber)
            bsIntegerPart();
        else
            bsFractionalPart();
        return this;
    }

    @Override
    public String toString() {

        return String.format("[%s@%s{%s}]", getClass().getSimpleName(), hashCode(),
        integerPartToString() + fractionalPartToString());
    }
    
    private void bsFractionalPart() {
        
         if (fractionalPart.length <= 1) {
            integerNumber = true;
            fractionalPart = new Integer[0];
            return;
        }
        fractionalPart = removeLastFromArray(fractionalPart);
   }
    
    private void bsIntegerPart() {
        
        if (integerPart.length <= 1) {
            integerPart = new Integer[0];
            return;
        }
        integerPart = removeLastFromArray(integerPart);
    }

    private void addDigitToFractionalPart(Integer digit) {

        if (integerNumber) return;
        fractionalPart = addToArray(fractionalPart, digit);
    }

    private void addDigitToIntegerPart(Integer digit) {

        if (digit == 0 && integerPart.length == 0) return;
        integerPart = addToArray(integerPart, digit);
    }
    
    private Integer[] addToArray(Integer[] array, Integer value) {
        
        Integer[] res = new Integer[array.length + 1];
        System.arraycopy(array, 0, res, 0, array.length);
        res[res.length - 1] = value;
        return res;
    }

    private Integer[] removeLastFromArray(Integer[] array) {

        Integer[] res = new Integer[array.length - 1];
        System.arraycopy(array, 0, res, 0, res.length);
        return res;
    }

    private String integerPartToString() {

        if (integerPart.length == 0) return "0";
        if (Arrays.stream(integerPart).allMatch(x -> x == 0)) return "0";
        return Arrays.stream(integerPart)
            .dropWhile(x -> x == 0)
            .map(String::valueOf)
            .reduce((x, y) -> x + y).get();
    }

    private String fractionalPartToString() {

        if (integerNumber || fractionalPart.length == 0) return "";
        return "." + Arrays.stream(fractionalPart)
            .map(String::valueOf)
            .reduce((x, y) -> x + y).get();
    }
}