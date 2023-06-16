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

    @Override
    public String toString() {

        return integerPartToString() + fractionalPartToString();
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
        Integer[] arr = reverse(fractionalPart);
        if (Arrays.stream(arr).allMatch(x -> x == 0)) return "";
        arr = Arrays.stream(arr)
            .dropWhile(x -> x == 0)
            .toArray(x -> new Integer[x]);
        arr = reverse(arr);
        return "." + Arrays.stream(arr)
            .map(String::valueOf)
            .reduce((x, y) -> x + y).get();
    }
    
    private static Integer[] reverse(Integer[] array) {
        
        Integer[] result = new Integer[array.length];
        for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
            result[i] = array[j];
        }
        return result;
    }
}