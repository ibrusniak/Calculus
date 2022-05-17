package com.ibrusniak.main;

/**
 * Number representation.
 * 1234567890.123456789. "leftDigits"."rightDigits"
 * Initially created Number is [0]
 * 
 * Operations:
 *  "addDigit" - adds digit to this number. Depends on flag
 *  "dotPresent" - digits will add to the left of right part
 *  "backSpace" - remove right digit from number
 * 
 */
public final class Number {

    private int[] leftDigits = new int[0];
    private int[] rightDigits = new int[0];
    private boolean dotPresent;

    public void addDigit(int digit) {
        if (dotPresent) {
            addDigitToRigthArray(digit);
        } else {
            addDigitToLeftArray(digit);
        }
    }

    public void dotPresent() {
        dotPresent = true;
    }

    private void addDigitToRigthArray(int digit) {
        wideningArray(leftDigits);
        leftDigits[leftDigits.length - 1] = digit;
    }

    private void addDigitToLeftArray(int digit) {
        wideningArray(rightDigits);
        rightDigits[rightDigits.length - 1] = digit;
    }

    private int[] wideningArray(int[] array) {
        int[] newArray = new int[array.length + 1];
        copyArray(array, newArray);
        return newArray;
    }

    private void copyArray(int[] source, int[] target) {
        for (int i = 0; i < source.length; i++) {
            target[i] = source[i];
        }
    }
}

