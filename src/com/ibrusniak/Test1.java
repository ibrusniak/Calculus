package com.ibrusniak;

import static com.ibrusniak.Utils.*;

public class Test1 {

    public static void main(String[] args) {

        NumberContainer num1 = new NumberContainer();
        
        println(num1);

        num1.addDigit(2)
            .addDigit(3)
            .addDigit(4)
            .addDigit(0)
            .addDigit(0)
            .addDigit(0);

        println(num1);

        num1.addDot();

        num1.addDigit(0)
        .addDigit(0)
        .addDigit(3)
        .addDigit(3)
        .addDigit(3)
        .addDigit(3)
        .addDigit(3)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(9)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0);

        println(num1);
    }
}