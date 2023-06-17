package com.ibrusniak;

import java.util.ArrayDeque;

public class Num {

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
}