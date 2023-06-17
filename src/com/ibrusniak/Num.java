package com.ibrusniak;

import java.util.Arrays;
import java.util.Locale;

public class Num {

    private boolean integerNumber = true;
    private boolean positiveNumber = true;
    private Integer[] integerPart = new Integer[0];
    private Integer[] fractionalPart = new Integer[0];

    public void addDot() { integerNumber = false; }

    public void setNegativeNumber() {

        positiveNumber = false;
    }

    public Num addDigit(Integer digit) {

        if (integerNumber)
            addDigitToIntegerPart(digit);
        else
            addDigitToFractionalPart(digit);
        return this;
    }

    public Num backSpace() {
        
        if (integerNumber)
            bsIntegerPart();
        else
            bsFractionalPart();
        return this;
    }

    @Override
    public String toString() {

        return String.format("[%s@%s{%s%s}]",
            getClass().getSimpleName(),
            hashCode(),
            positiveNumber ? "" : "-",
            integerPartToString() + fractionalPartToString());
    }
    
    @Override
    public boolean equals(Object other) {

        if (!(other instanceof Num)) return false;
        return
            (Arrays.compare(integerPart, ((Num)other).integerPart) == 0)
                &&
            (Arrays.compare(fractionalPart, ((Num)other).fractionalPart) == 0);
    }

    public boolean isZero() {

        if ((integerPart.length == 0 && fractionalPart.length == 0) ||
        (Arrays.stream(integerPart).allMatch(x -> x == 0)
            && Arrays.stream(fractionalPart).allMatch(x -> x == 0))) return true;
        return false;
    }

    public static Num fromDouble(Double i) {
        
        Double f = i;
        Num n = new Num();
        if (f < 0) {
            n.setNegativeNumber();
            f *= -1;
        }
        String str = String.format(Locale.US, "%308.325f", f);
        Integer[] intPart = Arrays.stream((str.substring(0, str.indexOf("."))).split(""))
            .map(Integer::valueOf)
            .toArray(x -> new Integer[x]);
        Integer[] fracPart = Arrays.stream((str.substring(str.indexOf(".") + 1, str.length())).split(""))
            .map(Integer::valueOf)
            .toArray(x -> new Integer[x]);
        fracPart = Arrays.stream(reverseArray(fracPart))
            .dropWhile(x -> x == 0)
            .toArray(x -> new Integer[x]);
        fracPart = reverseArray(fracPart);
        Arrays.stream(intPart)
            .forEach(n::addDigit);
        if(fracPart.length != 0) {
            n.addDot();
            Arrays.stream(fracPart)
                .forEach(n::addDigit);
        }
        return n;
    }

    public static Num fromInt(final Integer i) {
        
        Integer f = i;
        Num n = new Num();
        if (f < 0) {
            n.setNegativeNumber();
            f *= -1;
        }
        Arrays.stream(String.valueOf(f).split(""))
            .map(Integer::valueOf)
            .forEach(n::addDigit);
        return n;
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

    private static Integer[] reverseArray(Integer[] array) {

        Integer[] res = new Integer[array.length];
        for (int i = 0, j = array.length - 1; i < array.length; i++, j--)
            res[i] = array[j];
        return res;
    }
}