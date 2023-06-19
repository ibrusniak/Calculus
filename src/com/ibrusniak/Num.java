package com.ibrusniak;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Locale;

public class Num implements Comparable<Num> {

    private ArrayDeque<Integer> integerPart = new ArrayDeque<>();
    private ArrayDeque<Integer> fractionalPart = new ArrayDeque<>();
    private boolean isFractionalNumber = false;
    private boolean isPositiveNumber = true;

    public Num(int from) {

        if (from == Integer.MIN_VALUE)
            throw new Error("int argument too small!");
        if (from < 0) {
            from *= -1;
            isPositiveNumber = false;
        }
        Arrays.stream(String.valueOf(from).split(""))
            .map(Integer::valueOf)
            .forEach(this::addDigit);
    }
    
    public Num(double from) {

        if (from == Double.NEGATIVE_INFINITY || from == Double.POSITIVE_INFINITY) {
            throw new Error(String.format("Argument '%s' not allowed!", from));
        }
        if (from == Double.MIN_VALUE) from *= -1;
        if (from < 0) {
            from *= -1;
            isPositiveNumber = false;
        }
        String st = String.format(Locale.US, "%309.325f", from);
        // Add integer part of the number
        Arrays.stream(st.split("\\.")[0].split(""))
            .map(Integer::valueOf)
            .forEach(integerPart::add);

        

    }

    public void setIsFractionalNumber() {

        isFractionalNumber = true;
    }

    public void switchIsPositiveFlag() {

        isPositiveNumber = !isPositiveNumber;
    }

    public void addDigit(Integer digit) {

        if (isFractionalNumber)
            fractionalPart.addLast(digit);
        else
            integerPart.addLast(digit);
    }

    public void backspace() {

        if (isFractionalNumber) {
            fractionalPart.removeLast();
            if(fractionalPart.isEmpty()) isFractionalNumber = false;
        } else {
            integerPart.removeLast();
        }
    }

    @Override
    public String toString() {

        return
            (isPositiveNumber ? "" : "-")
            + (integerPart.isEmpty() ? "0" : integerPart.toString())
            + (fractionalPart.isEmpty() ? "" : "." + fractionalPart.toString());
    }

    @Override
    public int compareTo(Num o) {
        
        if (integerPart.size() != o.integerPart.size())
            return Integer.compare(integerPart.size(), o.integerPart.size());
        return Integer.compare(fullWeight(), o.fullWeight());
    }

    @Override
    public boolean equals(Object obj) {
        
        if (!(obj instanceof Num)) return false;
        return compareTo((Num)obj) == 0;
    }

    private int weight(ArrayDeque<Integer> integerOrFractionalPart) {
        
        int weight = 0;
        if (!integerOrFractionalPart.isEmpty()) {
            Object[] array = integerPart.toArray();
            for (int i = 0, positionWeight = array.length - 1; i < array.length; i++, positionWeight--) {
                weight += positionWeight * ((int)array[i]);
            }
        }
        return weight;
    }

    private int fullWeight() {
        return weight(integerPart) + weight(fractionalPart);
    }

    private String[] reverseArray(String[] source) {

        String[] target = new String[source.length];
        for (int i = 0, j = source.length - 1; i < source.length; i++, j--)
            target[i] = source[j];
        return target;
    }
}