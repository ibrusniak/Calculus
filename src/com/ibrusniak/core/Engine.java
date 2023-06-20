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

    public void backspace() {

        
    }

    private void keyPressDigitKeys(Key key) {}

    private void keyPressOperationKeys(Key key) {}

    private void keyPressControlKeys(Key key) {}

    @Override
    public String toString() {
        return buffer.toString();
    }
}
