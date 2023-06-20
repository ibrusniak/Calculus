package com.classes;

public class Calculator {

    public enum Key {

        DIGIT_ZERO, DIGIT_ONE,   DIGIT_TOW,
        DIGIT_TREE, DIGIT_FOUR,  DIGIT_FIVE,
        DIGIT_SIX,  DIGIT_SEVEN, DIGIT_EIGHT,
        DIGIT_NINE,

        DECIMAL_DOT,

        OP_SUM, OP_SUB, OP_MULT, OP_DIV,

        RESULT
    }

    private Num screen;

    @Override
    public String toString() {
        return screen.toString();
    }

    public void keyPress(Key key) {

    }
}