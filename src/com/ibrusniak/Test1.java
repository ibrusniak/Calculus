package com.ibrusniak;

import static com.ibrusniak.Utils.*;
import static com.ibrusniak.NumberProcessor.*;

public class Test1 {

    public static void main(String[] args) {

        // NumberContainer
            // operand1 = NumberContainer.fromInt(200),
            // operand2 = NumberContainer.fromInt(-403);
        
            // println(operand1, operand2, addition(operand1, operand2));
            // println(operand1, operand2, substraction(operand1, operand2));
            // println(operand1, operand2, musltiplication(operand1, operand2));
            // println(operand1, operand2, division(operand1, operand2));

        NumberContainer n = NumberContainer.fromInt(0000);
        n.addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(0)
        .addDigit(2)
        .addDigit(3);
        println(n);
        println(n.isZero());
        n.backSpace().backSpace();
        println(n);
        println(n.isZero());
    }
}