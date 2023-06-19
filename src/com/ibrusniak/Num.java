package com.ibrusniak;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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

        String[] stIntPart = st.split("\\.")[0].split("");
        String[] stFracPart = st.split("\\.")[1].split("");
        
        // Add integer part of the number
        Arrays.stream(stIntPart)
            .map(Integer::valueOf)
            .forEach(integerPart::add);

        /**
        * Fractional part algorithm:
        * reverse
        * remove redundand zeroes
        * reverse
        * add
        */
        
        // If all fractional part is zeroes
        if (Arrays.stream(stFracPart).allMatch(x -> x.equals("0"))) return;

        ArrayList<String> listFracPart = new ArrayList<>();
        Collections.addAll(listFracPart, stFracPart);
        Collections.reverse(listFracPart);
        List<String> l = listFracPart.stream().dropWhile(x -> x.equals("0")).toList();
        ArrayList<String> l2 = new ArrayList<>(l);
        Collections.reverse(l2);
        l2.stream()
            .map(Integer::valueOf)
            .forEach(fractionalPart::add);
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