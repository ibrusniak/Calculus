package com.ibrusniak.main;


import com.ibrusniak.utils.ArrayUtils;

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

    private int[] leftDigits = new int[] {0};
    private int[] rightDigits = new int[0];
    private boolean dotPresent = false;

    public void digit(int digit) {
        if (dotPresent) {
            rightDigits = ArrayUtils.add(rightDigits, digit);
        } else {
            if (leftDigits.length == 1 & leftDigits[0] == 0) {
                leftDigits = new int[0];
            }
            leftDigits = ArrayUtils.add(leftDigits, digit);
        }
    }

    public void bs() {
        if (dotPresent) {
            rightDigits = ArrayUtils.narrow(rightDigits, 1);
            normalize();
        } else {
            leftDigits = ArrayUtils.narrow(leftDigits, 1);
        }
    }

    public void dot() {
        dotPresent = true;
    }

    @Override
    public String toString() {
        normalize();
        String result = "";
        for (int i = 0; i < leftDigits.length; i++) {
            result += Integer.toString(leftDigits[i]);
        }
        if (dotPresent) {
            result += ".";
            for (int i = 0; i < rightDigits.length; i++) {
                result += Integer.toString(rightDigits[i]);
            }
        }
        return result;
    }

    public double toDouble() {
        normalize();
        String s = ArrayUtils.toString(leftDigits)
            + (dotPresent ? "." + ArrayUtils.toString(rightDigits): "");
        return Double.valueOf(s);
    }

    private void normalize() {
        if (rightDigits.length > 0) {
            int[] target = rightDigits;
            for (int i = rightDigits.length - 1; i >= 0; i--) {
                if (rightDigits[i] == 0) {
                    target = ArrayUtils.narrow(target, 1);
                } else {
                    break;
                }
            }
            rightDigits = target;
        }
        if (rightDigits.length == 0) {
            dotPresent = false;
        }
    }
}


