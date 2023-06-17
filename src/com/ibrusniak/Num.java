package com.ibrusniak;

import java.util.ArrayDeque;

public class Num implements Comparable<Num> {

    private ArrayDeque<Integer> integerPart = new ArrayDeque<>();
    private ArrayDeque<Integer> fractionalPart = new ArrayDeque<>();
    private boolean isFractionalNumber = false;
    private boolean isPositiveNumber = true;

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

        return (integerPart.isEmpty() ? "0" : integerPart.toString())
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
}