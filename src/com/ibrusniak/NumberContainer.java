package com.ibrusniak;

public class NumberContainer {

    private final int INT_PART_CAP = 10;
    private final int FRAC_PART_CAP = 10;
    private final String DECIMAL_DOT = ".";

    private Integer[] integerPart;
    private Integer[] fractionalPart;

    public NumberContainer() {
        integerPart = new Integer[INT_PART_CAP];
        fractionalPart = new Integer[FRAC_PART_CAP];
        fillByZeroesAll();
    }

    public NumberContainer(int integerPartCapacity, int fractionalPartCapacity) {
        integerPart = new Integer[integerPartCapacity];
        fractionalPart = new Integer[fractionalPartCapacity];
        fillByZeroesAll();
    }

    @Override
    public String toString() {
        String res = "";
        for (Integer i : integerPart)
            res += i;
        res += DECIMAL_DOT;
        for (Integer i : fractionalPart)
            res += i;
        return res;
    }

    private void fillByZeroesAll() {
        fillByZeroesIntegerPart();
        fillByZeroesFractionalPart();
    }

    private void fillByZeroesIntegerPart() {
        for (int i = 0; i < integerPart.length; i++)
            integerPart[i] = 0;
    }

    private void fillByZeroesFractionalPart() {
        for (int i = 0; i < fractionalPart.length; i++)
            fractionalPart[i] = 0;
    }

    private boolean allMatch(Integer[] arr, Integer value) {
        for (Integer i : arr)
            if (!i.equals(value)) return false;
        return true;
    }
}