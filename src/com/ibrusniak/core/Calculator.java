package com.ibrusniak.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public final class Calculator {
    
    private final int CAPACITY = 20;

    private ArrayDeque<String> screen = new ArrayDeque<>(CAPACITY);
    private ArrayDeque<String> operationalRegister = new ArrayDeque<>(CAPACITY);
    private ArrayDeque<String> memory = new ArrayDeque<>(CAPACITY);
    private boolean negative = false;
    private boolean screenInputCompleted = false;
    private Processor processor = new Processor();

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
            Key.C, Key.NEG, Key.MP, Key.MS, Key.MC, Key.MM, Key.MR);

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
        stringToKeyMap.put("MS", Key.MS);
        stringToKeyMap.put("MR", Key.MR);

        screen.addLast("0");
    }

    public enum Key {

        D0, D1, D2, D3, D4, D5, D6, D7, D8, D9,
        
        DOT, PLS, MNS, MUL, DIV, PER,
        RES, BS, CE, C, NEG, MP, MM, MC, MS, MR
    }

    /**
     * Input string of keys, separated by ' ' (space) to accelerate development
     * Example: "CE 1 2 3 + 2 3 ="
     * 
     * 
     * @param input
     * @see keyPressed(String k)
     * @trhows IllegalArgumentException
     */
    public void input(String input) {

        String[] keys = input.split("\\ ");
        for (String key : keys)
            keyPressed(key);
    }

    /**
     * 
     * Pressing the calculator's button
     * 
     * @param k - String, one of:
     *  digits: "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
     *  operations: "+", "-", "*", "/", "%"
     *  decimal dot: "."
     *  control keys: "=", "BS", "CE", "C", "+/-", "MP", "MM", "MC", "MR", "MS" 
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

        if (toString().equals("ERROR")) return;

        String digit = keyToDigitMap.get(key);

        if (screenInputCompleted) {
            resetScreen();
            screenInputCompleted = false;
        }
        switch (digit) {

            case "0":
                if (!screenToString().equals("0")) screen.addLast("0");
                break;
            default:
                if (screenToString().equals("0")) screen.removeLast();
                screen.addLast(digit);
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

        if (toString().equals("ERROR")) return;

        if (operation == null) {

            operationalRegister.clear();
            screen.stream().forEach(operationalRegister::add);
        } else {

            ArrayDeque<String> result = calculate(screen, operationalRegister);
            
            if (result.size() > CAPACITY) {
                setErrorState();
            } else {
                screen.clear();
                result.stream().forEach(screen::add);
                operationalRegister.clear();
                result.stream().forEach(operationalRegister::add);
            }
        }   
        
        operation = key;
        screenInputCompleted = true;
    }

    /**
     * Control keys:
     * Key.DOT, Key.RES, Key.BS, Key.CE,
     * Key.C, Key.NEG, Key.MP, Key.MC, Key.MM, Key.MR
     * 
     * @param key
     */
    private void keyPressControlKeys(Key key) {

        if (toString().equals("ERROR") && key != Key.C) return;

        if (key == Key.DOT) {dot();}
        else if (key == Key.RES) {result();}
        else if (key == Key.BS) {backspace();}
        else if (key == Key.CE) {clearEntry();}
        else if (key == Key.C) {clear();}
        else if (key == Key.NEG) {negative();}
        else if (key == Key.MP) {memAdd();}
        else if (key == Key.MC) {memClear();}
        else if (key == Key.MM) {memSub();}
        else if (key == Key.MR) {memRecall();}
        else if (key == Key.MS) {memStore();}
    }

    @Override
    public String toString() {

        return screenToString();
    }

    private void dot() {

        if (screenInputCompleted) {
            resetScreen();
            screenInputCompleted = false;
        }
        if(!screen.contains("."))
            screen.addLast(".");
    }

    private void result() {

        if (operation == null) {

            operationalRegister.clear();
            screen.stream().forEach(operationalRegister::add);
        } else {

            ArrayDeque<String> result = calculate(screen, operationalRegister);
            
            if (result.size() > CAPACITY) {
                setErrorState();
            } else {
                screen.clear();
                result.stream().forEach(screen::add);
            }
        }
        screenInputCompleted = true;
    }

    private void setErrorState() {

        clear();
        screen.clear();
        screen.addAll(Arrays.stream("ERROR".split("")).toList());
    }

    private ArrayDeque<String> calculate(ArrayDeque<String> op1, ArrayDeque<String> op2) {

        ArrayDeque<String> result = new ArrayDeque<String>(CAPACITY);
        if (operation == Key.PLS) {
            result = processor.makeAddition(op1, op2);
        } else if (operation == Key.MNS) {
            result = processor.makeSubtraction(op2, op1);
        } else if (operation == Key.MUL) {
            result = processor.makeMultiplication(op2, op1);
        } else {
            result = processor.makeDivision(op2, op1);
        }

        return result;
    }

    private void backspace() {

        if (!screenToString().equals("0")) screen.removeLast();
        if (screen.size() == 0) screen.addFirst("0");
    }

    private void clearEntry() {

        resetScreen();
        negative = false;
    }

    private void clear() {

        resetScreen();
        resetMemory();
        operation = null;
        operationalRegister.clear();
        screenInputCompleted = true;
        negative = false;
    }

    private void negative() {
        
        if (!screenToString().equals("0")) negative = !negative;
    }

    private void memAdd() {}

    private void memSub() {}
    
    private void memRecall() {}
    
    private void memStore() {}

    private void memClear() {

        resetMemory();
    }
    
    private void resetScreen() {

        screen.clear();
        screen.addLast("0");
    }

    private void resetMemory() {

        memory.clear();
        memory.addLast("0");
    }

    private String screenToString() {

        return (negative ? "-" : "") + screen.stream().reduce((x, y) -> x + y).get();
    }
}
