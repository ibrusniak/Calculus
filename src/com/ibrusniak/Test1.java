package com.ibrusniak;

import static com.ibrusniak.Utils.*;

public class Test1 {

    public static void main(String[] args) {

        Num num = new Num();
        
        num.addDigit(2);
        num.addDigit(3);
        num.setIsFractionalNumber();
        num.addDigit(5);
        num.addDigit(0);
        num.addDigit(9);
        println(num);
    }
}