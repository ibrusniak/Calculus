package com.ibrusniak.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public final class Engine {
    
    private final int CAPACITY = 40;

    private ArrayDeque<Integer> buffer = new ArrayDeque<>(CAPACITY);
    private ArrayDeque<Integer> memory = new ArrayDeque<>(CAPACITY);
    private boolean negative = false;

    private ArrayList<Key> digitKeys = new ArrayList<>();
    private ArrayList<Key> operationKeys = new ArrayList<>();
    private ArrayList<Key> controlKeys = new ArrayList<>();

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
    }

    public enum Key {

        D0, D1, D2, D3, D4, D5, D6, D7, D8, D9,
        
        DOT, PLS, MNS, MUL, DIV, PER,
        RES, BS, CE, C, NEG, MP, MM, MC, MR
    }

    public void keyPress(Key key) {

        if (digitKeys.contains(key)) { keyPressDigitKeys(key); }
        else if (operationKeys.contains(key)) { keyPressOperationKeys(key); }
        else if (controlKeys.contains(key)) { keyPressControlKeys(key); }
        else {
            throw new Error(String.format("Wrong key: {%s}!", key));
        }
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

        if (key == Key.D0) {d0();}
        else if (key == Key.D1) {d1();}
        else if (key == Key.D2) {d2();}
        else if (key == Key.D3) {d3();}
        else if (key == Key.D4) {d4();}
        else if (key == Key.D5) {d5();}
        else if (key == Key.D6) {d6();}
        else if (key == Key.D7) {d7();}
        else if (key == Key.D8) {d8();}
        else if (key == Key.D9) {d9();}
        else {
            throw new Error(String.format("Wrong key for function \"keyPressDigitKeys\": {%s}!", key));
        }
    }

    /**
     * Operation keys:
     * Key.PLS, Key.MNS, Key.MUL, Key.DIV, Key.PER
     * 
     * @param key
     */
    private void keyPressOperationKeys(Key key) {

        if (key == Key.PLS) {plus();}
        else if (key == Key.MNS) {minus();}
        else if (key == Key.MUL) {mult();}
        else if (key == Key.DIV) {div();}
        else if (key == Key.PER) {persent();}
        else {
            throw new Error(String.format("Wrong key for function \"keyPressOperationKeys\": {%s}!", key));
        }
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
        else if (key == Key.CE) {cleareverything();}
        else if (key == Key.C) {clear();}
        else if (key == Key.NEG) {negative();}
        else if (key == Key.MP) {memoryplus();}
        else if (key == Key.MC) {memoryclear();}
        else if (key == Key.MM) {memoryminus();}
        else if (key == Key.MR) {mr();}
        else {
            throw new Error(String.format("Wrong key for function \"keyPressControlKeys\": {%s}!", key));
        }
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    private void dot() {}
    private void result() {}
    private void backspace() {}
    private void cleareverything() {}
    private void clear() {}
    private void negative() {}
    private void memoryplus() {}
    private void memoryclear() {}
    private void memoryminus() {}
    private void mr() {}

    private void plus() {}
    private void minus() {}
    private void mult() {}
    private void div() {}
    private void persent() {}

    private void d0() {if(buffer.size() < CAPACITY) buffer.addLast(0);}
    private void d1() {if(buffer.size() < CAPACITY) buffer.addLast(1);}
    private void d2() {if(buffer.size() < CAPACITY) buffer.addLast(2);}
    private void d3() {if(buffer.size() < CAPACITY) buffer.addLast(3);}
    private void d4() {if(buffer.size() < CAPACITY) buffer.addLast(4);}
    private void d5() {if(buffer.size() < CAPACITY) buffer.addLast(5);}
    private void d6() {if(buffer.size() < CAPACITY) buffer.addLast(6);}
    private void d7() {if(buffer.size() < CAPACITY) buffer.addLast(7);}
    private void d8() {if(buffer.size() < CAPACITY) buffer.addLast(8);}
    private void d9() {if(buffer.size() < CAPACITY) buffer.addLast(9);}
}
