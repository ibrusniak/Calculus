package com.ibrusniak.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public final class Engine {
    
    private final int CAPACITY = 40;

    private ArrayDeque<String> buffer = new ArrayDeque<>(CAPACITY);
    private ArrayDeque<String> memory = new ArrayDeque<>(CAPACITY);
    private boolean negative = false;

    private Key operation = null;

    private ArrayList<Key> digitKeys = new ArrayList<>();
    private ArrayList<Key> operationKeys = new ArrayList<>();
    private ArrayList<Key> controlKeys = new ArrayList<>();
    private HashMap<Key, String> keyToDigitMap = new HashMap<>();
    private HashMap<String, Key> stringToKeyMap = new HashMap<>();

    {
        Collections.addAll(digitKeys,
            Key.D0, Key.D1, Key.D2, Key.D3,
            Key.D4, Key.D5, Key.D6, Key.D7,
            Key.D8, Key.D9);

        Collections.addAll(operationKeys,
            Key.PLS, Key.MNS, Key.MUL, Key.DIV, Key.PER);
        
        Collections.addAll(controlKeys,
            Key.DOT, Key.RES, Key.BS, Key.CE,
            Key.C, Key.NEG, Key.MP, Key.MC, Key.MM, Key.MR);

        keyToDigitMap.put(Key.D0, "0");
        keyToDigitMap.put(Key.D1, "1");
        keyToDigitMap.put(Key.D2, "2");
        keyToDigitMap.put(Key.D3, "3");
        keyToDigitMap.put(Key.D4, "4");
        keyToDigitMap.put(Key.D5, "5");
        keyToDigitMap.put(Key.D6, "6");
        keyToDigitMap.put(Key.D7, "7");
        keyToDigitMap.put(Key.D8, "8");
        keyToDigitMap.put(Key.D9, "9");

        stringToKeyMap.put("0", Key.D0);
        stringToKeyMap.put("1", Key.D1);
        stringToKeyMap.put("2", Key.D2);
        stringToKeyMap.put("3", Key.D3);
        stringToKeyMap.put("4", Key.D4);
        stringToKeyMap.put("5", Key.D5);
        stringToKeyMap.put("6", Key.D6);
        stringToKeyMap.put("7", Key.D7);
        stringToKeyMap.put("8", Key.D8);
        stringToKeyMap.put("9", Key.D9);

        stringToKeyMap.put(".", Key.DOT);
        stringToKeyMap.put("+", Key.PLS);
        stringToKeyMap.put("-", Key.MNS);
        stringToKeyMap.put("*", Key.MUL);
        stringToKeyMap.put("/", Key.DIV);
        stringToKeyMap.put("%", Key.PER);

        stringToKeyMap.put("=", Key.RES);
        stringToKeyMap.put("BS", Key.BS);
        stringToKeyMap.put("CE", Key.CE);
        stringToKeyMap.put("C", Key.C);
        stringToKeyMap.put("+/-", Key.NEG);
        stringToKeyMap.put("MP", Key.MP);
        stringToKeyMap.put("MM", Key.MM);
        stringToKeyMap.put("MC", Key.MC);
        stringToKeyMap.put("MR", Key.MR);

        buffer.addLast("0");
    }

    public enum Key {

        D0, D1, D2, D3, D4, D5, D6, D7, D8, D9,
        
        DOT, PLS, MNS, MUL, DIV, PER,
        RES, BS, CE, C, NEG, MP, MM, MC, MR
    }

    /**
     * 
     * Pressing the calculator's button
     * 
     * @param k - String, one of:
     *  digits: "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
     *  operations: "+", "-", "*", "/", "%"
     *  decimal dot: "."
     *  control keys: "=", "BS", "CE", "C", "+/-", "MP", "MM", "MC", "MR" 
     * 
     * @throws IllegalArgumentException
     */
    public void keyPressed(String k) {

        Key key = stringToKeyMap.get(k);
        if (key == null)
            throw new IllegalArgumentException(String.format("Illegal argument \"%s\"", k));
        keyPressed(key);
    }

    private void keyPressed(Key key) {

        if (digitKeys.contains(key)) { keyPressDigitKeys(key); }
        else if (operationKeys.contains(key)) { keyPressOperationKeys(key); }
        else if (controlKeys.contains(key)) { keyPressControlKeys(key); }
    }

    /**
     * Digit keys:
     *  Key.D0, Key.D1, Key.D2, Key.D3,
     *  Key.D4, Key.D5, Key.D6, Key.D7,
     *  Key.D8, Key.D9
     * 
     * @param key
     */
    private void keyPressDigitKeys(Key key) {

        String digit = keyToDigitMap.get(key);

        switch (digit) {

            case "0":
                if (!bufferToString().equals("0")) buffer.addLast("0");
                break;
            default:
                if (bufferToString().equals("0")) buffer.removeLast();
                buffer.addLast(digit);
                break;
        }
    }

    /**
     * Operation keys:
     * Key.PLS, Key.MNS, Key.MUL, Key.DIV, Key.PER
     * 
     * @param key
     */
    private void keyPressOperationKeys(Key key) {

        operation = key;
    }

    /**
     * Control keys:
     * Key.DOT, Key.RES, Key.BS, Key.CE,
     * Key.C, Key.NEG, Key.MP, Key.MC, Key.MM, Key.MR
     * 
     * @param key
     */
    private void keyPressControlKeys(Key key) {

        if (key == Key.DOT) {dot();}
        else if (key == Key.RES) {result();}
        else if (key == Key.BS) {backspace();}
        else if (key == Key.CE) {clearEntry();}
        else if (key == Key.C) {clear();}
        else if (key == Key.NEG) {negative();}
        else if (key == Key.MP) {memoryplus();}
        else if (key == Key.MC) {memoryclear();}
        else if (key == Key.MM) {memoryminus();}
        else if (key == Key.MR) {mr();}
    }

    @Override
    public String toString() {

        return bufferToString();
    }

    private void dot() {

        if(!buffer.contains("."))
            buffer.addLast(".");
    }

    private void result() {}

    private void backspace() {}

    private void clearEntry() {

        resetBuffer();
        negative = false;
    }

    private void clear() {

        resetBuffer();
        resetMemory();
        operation = null;
        negative = false;
    }

    private void negative() {
        
        if (!bufferToString().equals("0")) negative = !negative;
    }

    private void memoryplus() {}

    private void memoryclear() {

        memory.clear();
        memory.addLast("0");
    }

    private void memoryminus() {}

    private void mr() {}

    private void plus() {}

    private void minus() {}

    private void mult() {}

    private void div() {}

    private void persent() {}

    private void resetBuffer() {

        buffer.clear();
        buffer.addLast("0");
    }

    private void resetMemory() {

        memory.clear();
        memory.addLast("0");
    }

    private String bufferToString() {

        return (negative ? "-" : "") + buffer.stream().reduce((x, y) -> x + y).get();
    }
}
